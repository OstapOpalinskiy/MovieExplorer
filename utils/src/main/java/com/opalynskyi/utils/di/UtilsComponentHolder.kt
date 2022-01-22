package com.opalynskyi.utils.di

import com.gal.module_injector.ComponentHolder

object UtilsComponentHolder : ComponentHolder<UtilsApi, UtilsDependencies> {
    private var component: UtilsComponent? = null
    override fun init(dependencies: UtilsDependencies) {
        if (component == null) {
            synchronized(UtilsComponentHolder::class.java) {
                if (component == null) {
                    component = UtilsComponent.initAndGet(dependencies)
                }
            }
        }
    }

    override fun get(): UtilsApi {
        checkNotNull(component) { "NavigationComponent was not initialized!" }
        return component!!
    }

    override fun reset() {
        component = null
    }
}