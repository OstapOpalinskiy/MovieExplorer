package com.opalynskyi.cleanmovies.core.domain.user

import com.opalynskyi.cleanmovies.core.data.user.User
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<User>
    fun saveUser(user: User)
    fun clearUser()
}