package com.opalynskyi.utils.di

import com.opalynskyi.module_injector.ComponentHolder

object UtilsComponentHolder : ComponentHolder<UtilsFeatureApi, UtilsFeatureDependencies> {
    private var component: UtilsComponent? = null
    override fun init(dependencies: UtilsFeatureDependencies) {
        if (component == null) {
            synchronized(UtilsComponentHolder::class.java) {
                if (component == null) {
                    component = UtilsComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): UtilsFeatureApi {
        checkNotNull(component) { "NavigationComponent was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}