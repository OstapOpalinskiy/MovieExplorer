package com.opalynskyi.popular.di

import com.opalynskyi.injector.ComponentHolder
import com.opalynskyi.popular.api.MoviesPopularFeatureApi
import com.opalynskyi.popular.api.MoviesPopularFeatureDependencies

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
        checkNotNull(component) { "MoviesPopularFeatureApi was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}
