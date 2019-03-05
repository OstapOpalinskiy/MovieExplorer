package com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MovieListMapper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.createListWithHeaders
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.domain.movies.MovieEvent
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class FavouriteMoviesPresenter(
    private val moviesInteractor: MoviesInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val movieListMapper: MovieListMapper,
    private val dateTimeHelper: DateTimeHelper
) : FavouriteMoviesContract.Presenter {

    override var view: FavouriteMoviesContract.View? = null
    override var compositeDisposable: CompositeDisposable? = null


    override fun subscribeForEvents() {
        compositeDisposable?.add(moviesInteractor.bindEvents()
            .subscribeOn(schedulerProvider.backgroundThread())
            .observeOn(schedulerProvider.mainThread())
            .subscribeBy(
                onNext = { event ->
                    when (event) {
                        is MovieEvent.AddToFavorite -> getFavouriteMovies()
                        is MovieEvent.RemoveFromFavorite -> view?.removeItem(event.id)
                    }
                }
            ))
    }

    override fun onRefresh() {
        getFavouriteMovies()
    }

    override fun getFavouriteMovies() {
        compositeDisposable?.add(moviesInteractor.getFavourites()
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
                onError = { view?.showError(it.message!!) }
            ))
    }

    override fun removeFromFavourite(id: Int) {
        compositeDisposable?.add(moviesInteractor.removeFromFavourites(id)
            .observeOn(schedulerProvider.mainThread())
            .subscribeBy(
                onComplete = { Timber.d("Removed from favourite: $id") },
                onError = { view?.showError("Failed to remove from favourite") }
            ))
    }
}