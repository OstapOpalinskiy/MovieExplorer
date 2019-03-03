package com.opalynskyi.cleanmovies.app.di

import com.opalynskyi.cleanmovies.app.CleanMoviesApplication
import com.opalynskyi.cleanmovies.app.login.LoginComponent
import com.opalynskyi.cleanmovies.app.login.LoginModule
import com.opalynskyi.cleanmovies.app.mainscreen.MainScreenComponent
import com.opalynskyi.cleanmovies.app.mainscreen.MainScreenModule
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MoviesModule
import com.opalynskyi.cleanmovies.app.mainscreen.movies.all.AllMoviesComponent
import com.opalynskyi.cleanmovies.app.mainscreen.movies.all.AllMoviesModule
import com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite.FavouriteMoviesComponent
import com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite.FavouriteMoviesModule
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

    fun createAllMoviesComponent(allMoviesModule: AllMoviesModule, moviesModule: MoviesModule): AllMoviesComponent

    fun createFavouriteMoviesComponent(
        favouriteMoviesModule: FavouriteMoviesModule,
        moviesModule: MoviesModule
    ): FavouriteMoviesComponent
}