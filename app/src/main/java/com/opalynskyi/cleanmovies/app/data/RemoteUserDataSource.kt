package com.opalynskyi.cleanmovies.app.data

import com.opalynskyi.cleanmovies.app.domain.entities.User
import io.reactivex.Single

interface RemoteUserDataSource {
    fun getUser(): Single<User>
}

