package com.opalynskyi.cleanmovies.login

import io.reactivex.Completable

interface AuthProvider {
    fun login(): Completable
    fun logout()
    fun isLoggedIn(): Boolean
    fun notifyResult(result: LoginResultWrapper)
}