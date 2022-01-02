package com.opalynskyi.cleanmovies.domain

import com.opalynskyi.cleanmovies.domain.entities.User
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<User>
    fun saveUser(user: User)
    fun clearUser()
}