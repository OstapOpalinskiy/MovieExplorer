package com.opalynskyi.cleanmovies.core.domain

import io.reactivex.Completable
import io.reactivex.Single

interface AccountInteractor {
    fun login(): Completable
    fun logout()
    fun getPhoto(): Single<String>
}