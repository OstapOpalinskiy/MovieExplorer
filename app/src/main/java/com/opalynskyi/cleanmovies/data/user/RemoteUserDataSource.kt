package com.opalynskyi.cleanmovies.data.user

import com.opalynskyi.cleanmovies.domain.entities.User
import io.reactivex.Single

interface RemoteUserDataSource {
    fun getUser(): Single<User>
}

