package com.com.opalynskyi.favourites.di

import com.com.opalynskyi.favourites.api.MoviesFavouriteFeatureApi
import com.com.opalynskyi.favourites.api.MoviesFavouriteFeatureDependencies
import com.opalynskyi.injector.ComponentHolder

object MoviesFavouriteFeatureComponentHolder :
    ComponentHolder<MoviesFavouriteFeatureApi, MoviesFavouriteFeatureDependencies> {
    private var component: MoviesFavouriteFeatureComponent? = null

    override fun init(dependencies: MoviesFavouriteFeatureDependencies) {
        if (component == null) {
            synchronized(MoviesFavouriteFeatureComponentHolder::class.java) {
                if (component == null) {
                    component = MoviesFavouriteFeatureComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): MoviesFavouriteFeatureApi {
        checkNotNull(component) { "MoviesPopularFeatureApi was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}
