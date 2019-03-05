package com.opalynskyi.cleanmovies.app.di

import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.login.LoginComponent
import com.opalynskyi.cleanmovies.app.login.LoginModule
import com.opalynskyi.cleanmovies.app.mainscreen.MainScreenComponent
import com.opalynskyi.cleanmovies.app.mainscreen.MainScreenModule
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MoviesComponent
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MoviesModule
import dagger.Component
import javax.inject.Singleton

@Singleton
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