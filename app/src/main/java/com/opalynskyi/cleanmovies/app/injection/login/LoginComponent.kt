package com.opalynskyi.cleanmovies.app.injection.login

import com.opalynskyi.cleanmovies.app.injection.scopes.LoginActivityScope
import com.opalynskyi.cleanmovies.app.login.LoginActivity
import dagger.Subcomponent

@LoginActivityScope
@Subcomponent(
    modules = [
        LoginModule::class
    ]
)
interface LoginComponent {
    fun inject(activity: LoginActivity)
}