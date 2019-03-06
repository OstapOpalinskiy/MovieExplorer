package com.opalynskyi.cleanmovies.core.user.data

import com.opalynskyi.cleanmovies.core.user.domain.entities.User

interface LocalUserDataSource {
    var currentUser: User?
    fun clearCurrentUser()
}