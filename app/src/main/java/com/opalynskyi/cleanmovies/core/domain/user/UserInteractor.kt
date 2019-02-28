package com.opalynskyi.cleanmovies.core.domain.user

import com.opalynskyi.cleanmovies.core.data.user.User
import io.reactivex.Single

interface UserInteractor {
    fun getUser(): Single<User>
}