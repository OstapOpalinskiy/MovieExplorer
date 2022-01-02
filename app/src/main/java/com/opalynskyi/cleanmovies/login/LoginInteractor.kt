package com.opalynskyi.cleanmovies.login

import io.reactivex.Completable

interface LoginInteractor {
    fun login(): Completable
    fun logout()
    fun isLoggedIn(): Boolean
}