package com.opalynskyi.cleanmovies.core.user.domain

import com.opalynskyi.cleanmovies.core.user.domain.entities.User
import io.reactivex.Single

interface UserInteractor {
    fun getUser(): Single<User>
}