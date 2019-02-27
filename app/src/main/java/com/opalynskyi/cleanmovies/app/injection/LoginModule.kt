package com.opalynskyi.cleanmovies.app.injection

import com.opalynskyi.cleanmovies.app.LoginActivity
import com.opalynskyi.cleanmovies.app.login.FacebookHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoginModule(private val activity: LoginActivity) {

    @Singleton
    @Provides
    fun provideFacebookHelper(): FacebookHelper = FacebookHelper(activity)
}