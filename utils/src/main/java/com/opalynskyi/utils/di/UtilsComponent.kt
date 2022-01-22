package com.opalynskyi.utils.di

import dagger.Component

@Component(
    modules = [UtilsModule::class],
    dependencies = [UtilsDependencies::class]
)
abstract class UtilsComponent : UtilsApi {
    companion object {
        fun initAndGet(dependencies: UtilsDependencies): UtilsComponent {
            return DaggerUtilsComponent.builder()
                .utilsDependencies(dependencies)
                .build()
        }
    }
}