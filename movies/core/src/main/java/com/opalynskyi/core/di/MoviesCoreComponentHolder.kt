package com.opalynskyi.core.di

import com.opalynskyi.core.api.MoviesCoreFeatureApi
import com.opalynskyi.core.api.MoviesCoreFeatureDependencies
import com.opalynskyi.injector.ComponentHolder

object MoviesCoreComponentHolder :
    ComponentHolder<MoviesCoreFeatureApi, MoviesCoreFeatureDependencies> {
    private var component: MoviesCoreComponent? = null

    override fun init(dependencies: MoviesCoreFeatureDependencies) {
        if (component == null) {
            synchronized(MoviesCoreComponentHolder::class.java) {
                if (component == null) {
                    component = MoviesCoreComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): MoviesCoreFeatureApi {
        checkNotNull(component) { "MoviesCoreFeatureApi was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}
