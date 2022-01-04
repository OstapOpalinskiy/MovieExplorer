package com.opalynskyi.cleanmovies.presentation

import com.opalynskyi.cleanmovies.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.presentation.movies.favourites.FavouriteMoviesFragment
import com.opalynskyi.cleanmovies.presentation.movies.latest.LatestMoviesFragment
import dagger.Subcomponent

@MainScreenScope
@Subcomponent(
    modules = [
        MoviesModule::class
    ]
)
interface MoviesComponent {
    fun inject(fragment: FavouriteMoviesFragment)
    fun inject(fragment: LatestMoviesFragment)
}