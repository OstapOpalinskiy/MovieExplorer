package com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite

import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MoviesModule
import dagger.Subcomponent

@MainScreenScope
@Subcomponent(
    modules = [
        FavouriteMoviesModule::class,
        MoviesModule::class
    ]
)
interface FavouriteMoviesComponent {
    fun inject(fragment: FavouriteMoviesFragment)
}