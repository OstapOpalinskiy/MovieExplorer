package com.opalynskyi.cleanmovies.di

import com.opalynskyi.cleanmovies.CleanMoviesApplication
import com.opalynskyi.cleanmovies.di.scopes.ApplicationScope
import com.opalynskyi.cleanmovies.login.LoginComponent
import com.opalynskyi.cleanmovies.login.LoginModule
import com.opalynskyi.cleanmovies.presentation.MainScreenComponent
import com.opalynskyi.cleanmovies.presentation.MainScreenModule
import com.opalynskyi.cleanmovies.presentation.MoviesComponent
import com.opalynskyi.cleanmovies.presentation.MoviesModule
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