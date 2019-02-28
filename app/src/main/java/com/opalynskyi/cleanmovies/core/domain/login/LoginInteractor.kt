package com.opalynskyi.cleanmovies.core.domain.login

import io.reactivex.Completable

interface LoginInteractor {
    fun login(): Completable
    fun logout()
}