package com.opalynskyi.cleanmovies.app.login

import com.opalynskyi.cleanmovies.core.domain.login.AuthProvider
import com.opalynskyi.cleanmovies.core.domain.login.LoginInteractor
import com.opalynskyi.cleanmovies.core.domain.login.LoginResultWrapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy

class LoginPresenter(
    private val authProvider: AuthProvider,
    private val loginInteractor: LoginInteractor
) : LoginContract.Presenter {

    override var view: LoginContract.View? = null
    override val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun login() {
        compositeDisposable += loginInteractor.login().subscribeBy(
            onComplete = {
                view?.continueFlow()
            },
            onError = {
                view?.showLoginError(it.message!!)
            }
        )
    }

    override fun isLoggedin(): Boolean {
        return loginInteractor.isLoggedin()
    }

    override fun onActivityResult(result: LoginResultWrapper) {
        authProvider.notifyResult(result)
    }
}