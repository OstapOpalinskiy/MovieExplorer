package com.opalynskyi.cleanmovies.app.injection

import com.opalynskyi.cleanmovies.app.LoginActivity
import dagger.Subcomponent
import javax.inject.Singleton


@Singleton
@Subcomponent(
    modules = [
        LoginModule::class
    ]
)
interface LoginSubComponent {
    fun inject(activity: LoginActivity)
}