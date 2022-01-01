package com.opalynskyi.cleanmovies.app.data

import com.opalynskyi.cleanmovies.app.domain.entities.User
import com.opalynskyi.cleanmovies.app.domain.UserRepository
import io.reactivex.Single
import timber.log.Timber

class UserRepositoryImpl(
    private val localUserStorage: LocalUserDataSource,
    private val remoteUserDataSource: RemoteUserDataSource
) : UserRepository {

    override fun getUser(): Single<User> {
        val user = localUserStorage.currentUser
        return if (user != null) {
            Timber.d("Return local user: $user")
            Single.just(user)
        } else {
            Timber.d("Return remote user: $user")
            remoteUserDataSource.getUser()
        }
    }


    override fun saveUser(user: User) {
        localUserStorage.currentUser = user
    }


    override fun clearUser() {
        localUserStorage.clearCurrentUser()
    }
}