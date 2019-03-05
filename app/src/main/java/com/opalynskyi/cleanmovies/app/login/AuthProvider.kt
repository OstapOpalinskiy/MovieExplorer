package com.opalynskyi.cleanmovies.app.login

import io.reactivex.Completable

interface AuthProvider {
    fun login(): Completable
    fun logout()
    fun isLoggedin(): Boolean
    fun notifyResult(result: LoginResultWrapper)
}