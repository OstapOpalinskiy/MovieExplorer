package com.opalynskyi.cleanmovies.app.di

import com.opalynskyi.cleanmovies.app.di.movies.MoviesModule
import com.opalynskyi.cleanmovies.app.di.movies.UserModule
import com.opalynskyi.cleanmovies.app.di.scopes.MoviesActivityScope
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
}