package com.opalynskyi.cleanmovies.core.domain.user

import com.opalynskyi.cleanmovies.core.data.user.User
import io.reactivex.Single
import timber.log.Timber

class UserInteractorImpl(private val userRepository: UserRepository) : UserInteractor {

    override fun getUser(): Single<User> {
        return userRepository.getUser()
            .doOnSuccess {
                Timber.d("Save user on success: $it")
                userRepository.saveUser(it)
            }
    }
}