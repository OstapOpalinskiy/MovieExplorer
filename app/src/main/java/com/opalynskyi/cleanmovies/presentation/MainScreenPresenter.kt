package com.opalynskyi.cleanmovies.presentation

import com.opalynskyi.cleanmovies.SchedulerProvider
import com.opalynskyi.cleanmovies.domain.UserInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber

class MainScreenPresenter(
    private val userInteractor: UserInteractor,
    private val scheduler: SchedulerProvider
) : MainScreenContract.Presenter {

    override var view: MainScreenContract.View? = null

    override var compositeDisposable: CompositeDisposable? = null

    override fun loadUserPhoto() {
        Timber.d("loadUserPhoto")
        compositeDisposable?.add(userInteractor.getUser()
            .observeOn(scheduler.mainThread())
            .subscribeBy(
                onSuccess = {
                    Timber.d("loadUserPhoto success, view == $view")
                    view?.showPhoto(it.photoUrl)
                },
                onError = { view?.showError(it.message!!) }
            ))
    }
}