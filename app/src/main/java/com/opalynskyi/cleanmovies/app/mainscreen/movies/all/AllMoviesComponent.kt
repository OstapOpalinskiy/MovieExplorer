package com.opalynskyi.cleanmovies.app.mainscreen.movies.all

import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MoviesModule
import dagger.Subcomponent

@MainScreenScope
@Subcomponent(
    modules = [
        AllMoviesModule::class,
        MoviesModule::class
    ]
)
interface AllMoviesComponent {
    fun inject(fragment: AllMoviesFragment)
}