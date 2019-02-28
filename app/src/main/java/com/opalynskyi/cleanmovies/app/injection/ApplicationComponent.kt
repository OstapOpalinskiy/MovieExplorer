package com.opalynskyi.cleanmovies.app.injection

import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.injection.login.LoginComponent
import com.opalynskyi.cleanmovies.app.injection.login.LoginModule
import com.opalynskyi.cleanmovies.app.injection.movies.MoviesModule
import com.opalynskyi.cleanmovies.app.injection.movies.UserModule
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

    fun createMoviesScreenComponent(userModule: UserModule, moviesModule: MoviesModule): MoviesScreenComponent
}