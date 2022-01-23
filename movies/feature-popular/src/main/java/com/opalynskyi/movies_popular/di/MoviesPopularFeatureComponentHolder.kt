package com.opalynskyi.movies_popular.di

import com.opalynskyi.module_injector.ComponentHolder

object MoviesPopularFeatureComponentHolder :
    ComponentHolder<MoviesPopularFeatureApi, MoviesPopularFeatureDependencies> {
    private var component: MoviesPopularFeatureComponent? = null
    override fun init(dependencies: MoviesPopularFeatureDependencies) {
        if (component == null) {
            synchronized(MoviesPopularFeatureComponentHolder::class.java) {
                if (component == null) {
                    component = MoviesPopularFeatureComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): MoviesPopularFeatureApi {
        checkNotNull(component) { "NavigationComponent was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}