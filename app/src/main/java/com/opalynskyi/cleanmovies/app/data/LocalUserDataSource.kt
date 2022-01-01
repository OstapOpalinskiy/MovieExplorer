package com.opalynskyi.cleanmovies.app.data

import com.opalynskyi.cleanmovies.app.domain.entities.User

interface LocalUserDataSource {
    var currentUser: User?
    fun clearCurrentUser()
}