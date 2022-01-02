package com.opalynskyi.cleanmovies.domain

import com.opalynskyi.cleanmovies.domain.entities.User
import io.reactivex.Single

interface UserInteractor {
    fun getUser(): Single<User>
}