package com.opalynskyi.cleanmovies.app.login

import io.reactivex.Completable
import io.reactivex.Single

interface AccountHelper {
    fun login(): Completable
    fun logout()
    fun getPhoto(): Single<String>
}