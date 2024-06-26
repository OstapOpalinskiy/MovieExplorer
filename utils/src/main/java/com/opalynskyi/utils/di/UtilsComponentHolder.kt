package com.opalynskyi.utils.di

import com.opalynskyi.injector.ComponentHolder
import com.opalynskyi.utils.api.UtilsFeatureApi
import com.opalynskyi.utils.api.UtilsFeatureDependencies

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
        checkNotNull(component) { "Utils feature was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}
