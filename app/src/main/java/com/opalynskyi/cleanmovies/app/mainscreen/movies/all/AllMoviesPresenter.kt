package com.opalynskyi.cleanmovies.app.mainscreen.movies.all

import com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter.MovieItem
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class AllMoviesPresenter(
    private val moviesInteractor: MoviesInteractor,
    private val schedulerProvider: SchedulerProvider
) : AllMoviesContract.Presenter {
    override var view: AllMoviesContract.View? = null
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun getMovies() {
        val startDate = "2018-09-15"
        val endDate = "2018-10-22"
        compositeDisposable += moviesInteractor
            .getMovies(startDate, endDate)
            .observeOn(schedulerProvider.mainThread())
            .subscribeBy(
                onSuccess = { view?.showMovies(it.map { movie -> MovieItem.fromMovie(movie) }) },
                onError = { view?.showError(it.message!!) }
            )
    }
}