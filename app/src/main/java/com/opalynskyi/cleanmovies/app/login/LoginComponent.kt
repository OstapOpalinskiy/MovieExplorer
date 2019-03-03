package com.opalynskyi.cleanmovies.app.login

import com.opalynskyi.cleanmovies.app.di.scopes.LoginScreenScope
import dagger.Subcomponent

@LoginScreenScope
@Subcomponent(
    modules = [
        LoginModule::class
    ]
)
interface LoginComponent {
    fun inject(activity: LoginActivity)
}