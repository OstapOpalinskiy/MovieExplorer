package com.opalynskyi.utils.di

import dagger.Component

@Component(
    modules = [UtilsModule::class],
    dependencies = [UtilsFeatureDependencies::class]
)
internal abstract class UtilsComponent : UtilsFeatureApi {
    companion object {
        fun initAndGet(featureDependencies: UtilsFeatureDependencies): UtilsComponent {
            return DaggerUtilsComponent.builder()
                .utilsFeatureDependencies(featureDependencies)
                .build()
        }
    }
}