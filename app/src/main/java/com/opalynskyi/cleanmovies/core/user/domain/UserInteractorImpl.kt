package com.opalynskyi.cleanmovies.core.user.domain

import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.user.domain.entities.User
import io.reactivex.Single
import timber.log.Timber

class UserInteractorImpl(
    private val repository: UserRepository,
    private val scheduler: SchedulerProvider
) : UserInteractor {

    override fun getUser(): Single<User> {
        return repository.getUser()
            .subscribeOn(scheduler.backgroundThread())
            .doOnSuccess {
                Timber.d("Save user on success: $it")
                repository.saveUser(it)
            }
    }
}