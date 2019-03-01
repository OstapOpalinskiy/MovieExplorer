package com.opalynskyi.cleanmovies.core.domain.login

import io.reactivex.Completable

class LoginInteractorImpl(
    private val authProvider: AuthProvider
) : LoginInteractor {

    override fun login(): Completable {
        return authProvider.login()
    }

    override fun logout() {
        authProvider.logout()
    }

    override fun isLoggedin(): Boolean {
        return authProvider.isLoggedin()
    }
}