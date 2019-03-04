package com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MovieListMapper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.ItemType
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.ListItem
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.MovieItem
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.MovieItemComparator
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.domain.movies.MovieEvent
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class FavouriteMoviesPresenter(
    private val moviesInteractor: MoviesInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val movieListMapper: MovieListMapper,
    private val dateTimeHelper: DateTimeHelper
) : FavouriteMoviesContract.Presenter {

    override fun subscribeForEvents() {
        compositeDisposable += moviesInteractor.bindEvents()
            .subscribeOn(schedulerProvider.backgroundThread())
            .observeOn(schedulerProvider.mainThread())
            .subscribeBy(
                onNext = { event ->
                    when (event) {
                        is MovieEvent.AddToFavorite -> getFavouriteMovies()
                        is MovieEvent.RemoveFromFavorite -> view?.removeItem(event.id)
                    }
                }
            )
    }

    override var view: FavouriteMoviesContract.View? = null
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getFavouriteMovies() {
        compositeDisposable += moviesInteractor
            .getFavourites()
            .observeOn(schedulerProvider.mainThread())
            .subscribeBy(
                onSuccess = { movies ->
                    val movieItems = movies.map(movieListMapper::mapToMovieItem)
                    view?.showMovies(createListWithHeaders(movieItems))
                },
                onError = { view?.showError(it.message!!) }
            )
    }

    private fun createListWithHeaders(movieItems: List<MovieItem>): List<ListItem> {
        val listWithHeaders = mutableListOf<ListItem>()
        val sortedMovieItems = movieItems.sortedWith(MovieItemComparator)
        var headerMonth = 0
        var header: ListItem? = null
        for (i in 0 until sortedMovieItems.size) {
            val currentMovie = sortedMovieItems[i]
            if (headerMonth == 0 || headerMonth != currentMovie.month) {
                headerMonth = currentMovie.month
                header = ListItem(
                    type = ItemType.HEADER,
                    headerTitle = dateTimeHelper.getHeaderDate(currentMovie.releaseDate)
                )
                listWithHeaders.add(header)
            }
            val item = ListItem(type = ItemType.ITEM, movie = currentMovie, header = header)
            header?.children?.add(item)
            listWithHeaders.add(item)

        }
        return listWithHeaders
    }

    override fun removeFromFavourite(id: Int) {
        compositeDisposable += moviesInteractor.removeFromFavourites(id).subscribeBy(
            onComplete = { Timber.d("Removed from favourite: $id") },
            onError = { view?.showError("Failed to remove from favourite") }
        )
    }

    override fun share() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}