package com.opalynskyi.cleanmovies.di

import com.opalynskyi.cleanmovies.App
import com.opalynskyi.cleanmovies.di.scopes.ApplicationScope
import com.opalynskyi.cleanmovies.presentation.MainScreenComponent
import com.opalynskyi.cleanmovies.presentation.MainScreenModule
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ApplicationModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: App)

    fun createMainScreenComponent(module: MainScreenModule): MainScreenComponent
}