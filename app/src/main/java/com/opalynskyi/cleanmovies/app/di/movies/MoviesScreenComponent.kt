package com.opalynskyi.cleanmovies.app.di.movies

import com.opalynskyi.cleanmovies.app.di.scopes.MoviesActivityScope
import com.opalynskyi.cleanmovies.app.movies.AllMoviesFragment
import com.opalynskyi.cleanmovies.app.movies.FavouriteMoviesFragment
import com.opalynskyi.cleanmovies.app.movies.MoviesActivity
import dagger.Subcomponent

@MoviesActivityScope
@Subcomponent(
    modules = [
        UserModule::class,
        MoviesModule::class
    ]
)
interface MoviesScreenComponent {
    fun inject(activity: MoviesActivity)
    fun injectAllMoviesFragment(fragment: AllMoviesFragment)
    fun injectFavouriteMoviesFragment(fragment: FavouriteMoviesFragment)
}