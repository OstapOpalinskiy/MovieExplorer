package com.opalynskyi.cleanmovies.login

import com.opalynskyi.cleanmovies.di.scopes.LoginScreenScope
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