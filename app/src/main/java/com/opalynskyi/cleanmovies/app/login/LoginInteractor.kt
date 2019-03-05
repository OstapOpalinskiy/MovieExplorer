package com.opalynskyi.cleanmovies.app.login

import io.reactivex.Completable

interface LoginInteractor {
    fun login(): Completable
    fun logout()
    fun isLoggedin(): Boolean
}