package com.opalynskyi.cleanmovies.login

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class LoginPresenter(
    private val authProvider: AuthProvider,
    private val loginInteractor: LoginInteractor
) : LoginContract.Presenter {

    override var view: LoginContract.View? = null
    override var compositeDisposable: CompositeDisposable? = null


    override fun login() {
        compositeDisposable?.add(loginInteractor.login().subscribeBy(
            onComplete = {
                view?.continueFlow()
            },
            onError = {
                view?.showLoginError(it.message!!)
            }
        ))
    }

    override fun isLoggedIn(): Boolean {
        return loginInteractor.isLoggedIn()
    }

    override fun onActivityResult(result: LoginResultWrapper) {
        authProvider.notifyResult(result)
    }
}