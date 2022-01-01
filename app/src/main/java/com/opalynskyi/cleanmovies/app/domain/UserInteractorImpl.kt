package com.opalynskyi.cleanmovies.app.domain

import com.opalynskyi.cleanmovies.app.SchedulerProvider
import com.opalynskyi.cleanmovies.app.domain.entities.User
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