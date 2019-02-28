package com.opalynskyi.cleanmovies.app.injection

import com.opalynskyi.cleanmovies.app.injection.movies.MoviesModule
import com.opalynskyi.cleanmovies.app.injection.movies.UserModule
import com.opalynskyi.cleanmovies.app.movies.MoviesActivity
import dagger.Subcomponent


@Subcomponent(
    modules = [
        UserModule::class,
        MoviesModule::class
    ]
)
interface MoviesScreenComponent {
    fun inject(activity: MoviesActivity)
}