package com.opalynskyi.cleanmovies.presentation.movies

import com.opalynskyi.cleanmovies.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.presentation.movies.favourites.FavouriteMoviesFragment
import com.opalynskyi.cleanmovies.presentation.movies.popular.PopularMoviesFragment
import dagger.Subcomponent

@MainScreenScope
@Subcomponent(
    modules = [
        MoviesModule::class
    ]
)
interface MoviesComponent {
    fun inject(fragment: PopularMoviesFragment)
    fun inject(fragment: FavouriteMoviesFragment)
}