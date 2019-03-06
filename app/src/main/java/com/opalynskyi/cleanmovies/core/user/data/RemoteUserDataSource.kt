package com.opalynskyi.cleanmovies.core.user.data

import com.opalynskyi.cleanmovies.core.user.domain.entities.User
import io.reactivex.Single

interface RemoteUserDataSource {
    fun getUser(): Single<User>
}

