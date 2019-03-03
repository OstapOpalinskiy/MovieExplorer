package com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite

import com.opalynskyi.cleanmovies.app.mainscreen.movies.MovieListMapper
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class FavouriteMoviesPresenter(
    private val moviesInteractor: MoviesInteractor,
    private val schedulerProvider: SchedulerProvider,
    private val movieListMapper: MovieListMapper
) : FavouriteMoviesContract.Presenter {
    override var view: FavouriteMoviesContract.View? = null
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun getMovies() {
        val startDate = "2018-09-31"
        val endDate = "2018-12-22"
//        compositeDisposable += moviesInteractor
//            .getFavourites()
//            .observeOn(schedulerProvider.mainThread())
//            .subscribeBy(
//                onSuccess = { view?.showMovies(it.map(movieListMapper::mapToMovieItem)) },
//                onError = { view?.showError(it.message!!) }
//            )
    }
}