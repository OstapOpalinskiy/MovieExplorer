package com.opalynskyi.cleanmovies.core.data.user

import io.reactivex.Single

interface RemoteUserDataSource {
    fun getUser(): Single<User>
}

