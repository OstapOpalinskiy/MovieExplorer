package com.opalynskyi.cleanmovies.app.di

import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.di.scopes.ApplicationScope
import com.opalynskyi.cleanmovies.app.login.LoginComponent
import com.opalynskyi.cleanmovies.app.login.LoginModule
import com.opalynskyi.cleanmovies.app.presentation.MainScreenComponent
import com.opalynskyi.cleanmovies.app.presentation.MainScreenModule
import com.opalynskyi.cleanmovies.app.presentation.MoviesComponent
import com.opalynskyi.cleanmovies.app.presentation.MoviesModule
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ApplicationModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: CleanMoviesApplication)

    fun createLoginComponent(module: LoginModule): LoginComponent

    fun createMainScreenComponent(userModule: MainScreenModule): MainScreenComponent

    fun createMoviesComponent(
        moviesModule: MoviesModule
    ): MoviesComponent
}