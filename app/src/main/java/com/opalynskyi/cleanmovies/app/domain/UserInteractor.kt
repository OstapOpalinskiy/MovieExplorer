package com.opalynskyi.cleanmovies.app.domain

import com.opalynskyi.cleanmovies.app.domain.entities.User
import io.reactivex.Single

interface UserInteractor {
    fun getUser(): Single<User>
}