package com.opalynskyi.cleanmovies.app.mainscreen.movies.all

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MovieListMapper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.ItemType
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.ListItem
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.MovieItem
import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.MovieItemComparator
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class AllMoviesPresenter(
    private val moviesInteractor: MoviesInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val movieListMapper: MovieListMapper,
    private val dateTimeHelper: DateTimeHelper
) : AllMoviesContract.Presenter {

    override var view: AllMoviesContract.View? = null
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun getMovies() {
        val startDate = "2018-09-15"
        val endDate = "2018-12-22"
        compositeDisposable += moviesInteractor
            .getMovies(startDate, endDate)
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

    override fun addToFavourite(id: Int) {
        compositeDisposable += moviesInteractor.addToFavourites(id)
            .observeOn(schedulerProvider.mainThread())
            .subscribeBy(
                onComplete = {
                    view?.notifyItemIsFavourite(id)
                    view?.showMessage("Added to favourite")
                },
                onError = {
                    view?.showError("Failed add to favourite")
                }
            )

    }

    override fun share() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}