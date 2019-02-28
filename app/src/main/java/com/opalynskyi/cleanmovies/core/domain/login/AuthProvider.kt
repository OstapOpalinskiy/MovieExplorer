package com.opalynskyi.cleanmovies.core.domain.login

import io.reactivex.Completable

interface AuthProvider {
    fun login(): Completable
    fun logout()
    fun notifyResult(result: LoginResultWrapper)
}