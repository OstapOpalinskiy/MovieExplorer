package com.opalynskyi.cleanmovies.app.presentation

import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.presentation.moviesList.AllMoviesFragment
import com.opalynskyi.cleanmovies.app.presentation.favourites.FavouriteMoviesFragment
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