package com.opalynskyi.cleanmovies.app.login

import com.opalynskyi.cleanmovies.app.di.scopes.LoginScreenScope
import com.opalynskyi.cleanmovies.core.domain.login.AuthProvider
import com.opalynskyi.cleanmovies.core.domain.login.LoginInteractor
import com.opalynskyi.cleanmovies.core.domain.login.LoginInteractorImpl
import dagger.Module
import dagger.Provides

@Module
class LoginModule(private val activity: LoginActivity) {
    @Provides
    @LoginScreenScope
    fun provideLoginPresenter(
        authProvider: AuthProvider,
        loginInteractor: LoginInteractor
    ): LoginContract.Presenter =
        LoginPresenter(authProvider, loginInteractor)

    @Provides
    @LoginScreenScope
    fun provideAuthProvider(): AuthProvider = FacebookAuthProvider(activity)

    @Provides
    @LoginScreenScope
    fun provideLoginInteractor(authProvider: AuthProvider): LoginInteractor = LoginInteractorImpl(authProvider)
}