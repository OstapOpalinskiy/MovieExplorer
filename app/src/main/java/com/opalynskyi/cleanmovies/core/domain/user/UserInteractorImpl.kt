package com.opalynskyi.cleanmovies.core.domain.user

import com.opalynskyi.cleanmovies.core.data.user.User
import io.reactivex.Single

class UserInteractorImpl(private val userRepository: UserRepository) : UserInteractor {

    override fun getUser(): Single<User> {
        return userRepository.getUser()
            .doOnSuccess { userRepository.saveUser(it) }
    }
}