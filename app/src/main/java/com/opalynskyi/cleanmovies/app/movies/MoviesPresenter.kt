package com.opalynskyi.cleanmovies.app.movies

import com.opalynskyi.cleanmovies.core.domain.user.UserInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class MoviesPresenter(private val userInteractor: UserInteractor) : MoviesContract.Presenter {

    override var view: MoviesContract.View? = null

    override val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun loadUserPhoto() {
        compositeDisposable += userInteractor.getUser().subscribeBy(
            onSuccess = { view?.showPhoto(it.photoUrl) },
            onError = { view?.showError(it.message!!) }
        )
    }
}