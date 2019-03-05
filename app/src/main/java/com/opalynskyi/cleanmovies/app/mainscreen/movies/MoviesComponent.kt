package com.opalynskyi.cleanmovies.app.mainscreen.movies

import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.mainscreen.movies.all.AllMoviesFragment
import com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite.FavouriteMoviesFragment
import dagger.Subcomponent

@MainScreenScope
@Subcomponent(
    modules = [
        MoviesModule::class
    ]
)
interface MoviesComponent {
    fun inject(fragment: FavouriteMoviesFragment)
    fun inject(fragment: AllMoviesFragment)
}