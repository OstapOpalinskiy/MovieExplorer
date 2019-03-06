package com.opalynskyi.cleanmovies.core.user.domain

import com.opalynskyi.cleanmovies.core.user.domain.entities.User
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<User>
    fun saveUser(user: User)
    fun clearUser()
}