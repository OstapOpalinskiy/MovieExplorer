package com.opalynskyi.cleanmovies.app.mainscreen.movies.all

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MovieListMapper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.createListWithHeaders
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.movies.domain.MovieEvent
import com.opalynskyi.cleanmovies.core.movies.domain.MoviesInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class AllMoviesPresenter(
    private val moviesInteractor: MoviesInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val movieListMapper: MovieListMapper,
    private val dateTimeHelper: DateTimeHelper
) : AllMoviesContract.Presenter {

    override var view: AllMoviesContract.View? = null
    override var compositeDisposable: CompositeDisposable? = null

    override fun subscribeForEvents() {
        compositeDisposable?.add(moviesInteractor.bindEvents()
            .subscribeOn(schedulerProvider.backgroundThread())
            .observeOn(schedulerProvider.mainThread())
            .subscribeBy(
                onNext = { event ->
                    when (event) {
                        is MovieEvent.AddToFavorite -> view?.notifyIsAdded(event.id)
                        is MovieEvent.RemoveFromFavorite -> view?.notifyIsRemoved(event.id)
                    }
                }
            ))
    }

    override fun onRefresh() {
        getStartEndDate()
    }

    override fun getMovies() {
        val dateRange = getStartEndDate()
        compositeDisposable?.add(moviesInteractor
            .getMovies(dateRange.first, dateRange.second)
            .observeOn(schedulerProvider.mainThread())
            .subscribeBy(
                onSuccess = { movies ->
                    val movieItems = movies.map(movieListMapper::mapToMovieItem)
                    if (movieItems.isEmpty()) {
                        view?.showEmptyState()
                    } else {
                        view?.showMovies(createListWithHeaders(dateTimeHelper, movieItems))
                    }
                    view?.hideProgress()
                },
                onError = {
                    view?.showError(it.message!!)
                }
            ))
    }

    private fun getStartEndDate(): Pair<String, String> {
        val startDateMillis = dateTimeHelper.getMonthFromToday(NUMBER_MONTH_FROM_NOW)
        val startDate = dateTimeHelper.getServerDate(startDateMillis)
        val endDate = dateTimeHelper.getServerDate(System.currentTimeMillis())
        return startDate to endDate
    }

    override fun addToFavourite(id: Int) {
        compositeDisposable?.add(moviesInteractor.addToFavourites(id)
            .observeOn(schedulerProvider.mainThread())
            .subscribeBy(
                onComplete = {
                    view?.notifyItemIsFavourite(id)
                },
                onError = {
                    view?.showError("Failed add to favourite")
                }
            ))
    }

    override fun removeFromFavourite(id: Int) {
        compositeDisposable?.add(moviesInteractor
            .removeFromFavourites(id)
            .observeOn(schedulerProvider.mainThread())
            .subscribeBy(
                onComplete = { view?.notifyIsRemoved(id) },
                onError = { view?.showError("Failed to remove from favourite") }
            ))
    }

    companion object {
        private const val NUMBER_MONTH_FROM_NOW = 3
    }
}