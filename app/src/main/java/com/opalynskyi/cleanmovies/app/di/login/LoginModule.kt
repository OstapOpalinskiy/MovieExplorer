package com.opalynskyi.cleanmovies.app.di.login

import com.opalynskyi.cleanmovies.app.di.scopes.LoginActivityScope
import com.opalynskyi.cleanmovies.app.login.FacebookAuthProvider
import com.opalynskyi.cleanmovies.app.login.LoginActivity
import com.opalynskyi.cleanmovies.app.login.LoginContract
import com.opalynskyi.cleanmovies.app.login.LoginPresenter
import com.opalynskyi.cleanmovies.core.domain.login.AuthProvider
import com.opalynskyi.cleanmovies.core.domain.login.LoginInteractor
import com.opalynskyi.cleanmovies.core.domain.login.LoginInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class LoginModule(private val activity: LoginActivity) {
    @Provides
    @LoginActivityScope
    fun provideLoginPresenter(
        authProvider: AuthProvider,
        loginInteractor: LoginInteractor
    ): LoginContract.Presenter =
        LoginPresenter(authProvider, loginInteractor)

    @Provides
    @LoginActivityScope
    fun provideAuthProvider(): AuthProvider = FacebookAuthProvider(activity)

    @Provides
    @LoginActivityScope
    fun provideLoginInteractor(authProvider: AuthProvider): LoginInteractor = LoginInteractorImpl(authProvider)
}