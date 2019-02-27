package com.opalynskyi.cleanmovies.core.domain

import com.opalynskyi.cleanmovies.app.login.AccountHelper
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AccountInteractorImpl @Inject constructor(private val accountHelper: AccountHelper) : AccountInteractor {

    override fun login(): Completable {
        return accountHelper.login()
    }

    override fun logout() {
        return accountHelper.logout()
    }

    override fun getPhoto(): Single<String> {
        return accountHelper.getPhoto()
    }
}