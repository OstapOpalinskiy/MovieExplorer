package com.opalynskyi.cleanmovies.app.login

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

    override fun isLoggedIn(): Boolean {
        return authProvider.isLoggedIn()
    }
}