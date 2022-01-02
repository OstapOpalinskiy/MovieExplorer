package com.opalynskyi.cleanmovies.data.user

import com.opalynskyi.cleanmovies.domain.entities.User

interface LocalUserDataSource {
    var currentUser: User?
    fun clearCurrentUser()
}