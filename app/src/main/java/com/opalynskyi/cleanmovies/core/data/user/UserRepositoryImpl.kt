package com.opalynskyi.cleanmovies.core.data.user

import com.opalynskyi.cleanmovies.core.domain.user.UserRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserRepositoryImpl(
    private val localUserStorage: LocalUserDataSource,
    private val remoteUserDataSource: RemoteUserDataSource
) : UserRepository {

    override fun getUser(): Single<User> {
        val user = localUserStorage.currentUser
        return if (user != null) {
            Single.just(user)
        } else {
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