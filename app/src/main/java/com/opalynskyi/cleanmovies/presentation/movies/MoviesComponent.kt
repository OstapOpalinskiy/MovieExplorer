package com.opalynskyi.cleanmovies.presentation.movies

import com.opalynskyi.cleanmovies.di.scopes.MainScreenScope
import dagger.Subcomponent

@MainScreenScope
@Subcomponent(
    modules = [
        MoviesModule::class
    ]
)
interface MoviesComponent {
    fun inject(fragment: LatestMoviesFragment)
}