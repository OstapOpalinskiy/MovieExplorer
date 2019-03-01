package com.opalynskyi.cleanmovies.app.movies

import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import com.opalynskyi.cleanmovies.core.domain.user.UserInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MoviesPresenter(
    private val userInteractor: UserInteractor,
    private val moviesInteractor: MoviesInteractor
) : MoviesContract.Presenter {

    override var view: MoviesContract.View? = null

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun loadUserPhoto() {
        compositeDisposable += userInteractor.getUser().subscribeBy(
            onSuccess = { view?.showPhoto(it.photoUrl) },
            onError = { view?.showError(it.message!!) }
        )
    }


    override fun getMovies() {
        val startDate = "2018-09-15"
        val endDate = "2018-10-22"
        compositeDisposable += moviesInteractor
            .getMovies(startDate, endDate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { view?.showMovies(it) },
                onError = { view?.showError(it.message!!) }
            )
    }
}