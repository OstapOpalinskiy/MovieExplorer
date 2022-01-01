package com.opalynskyi.cleanmovies.app.domain

import com.opalynskyi.cleanmovies.app.domain.entities.User
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<User>
    fun saveUser(user: User)
    fun clearUser()
}