package com.opalynskyi.cleanmovies.app.injection

import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.injection.scopes.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        AndroidModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: CleanMoviesApplication)

    fun createLoginSubComponent(module: LoginModule) : LoginSubComponent
}