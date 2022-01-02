package com.opalynskyi.cleanmovies.presentation

import com.opalynskyi.cleanmovies.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.presentation.moviesList.AllMoviesFragment
import com.opalynskyi.cleanmovies.presentation.favourites.FavouriteMoviesFragment
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