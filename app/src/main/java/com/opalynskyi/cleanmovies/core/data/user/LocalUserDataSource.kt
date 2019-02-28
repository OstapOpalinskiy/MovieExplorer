package com.opalynskyi.cleanmovies.core.data.user

interface LocalUserDataSource {
    var currentUser: User?
    fun clearCurrentUser()
}