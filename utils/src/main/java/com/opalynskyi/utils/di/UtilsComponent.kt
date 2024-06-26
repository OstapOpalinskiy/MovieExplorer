package com.opalynskyi.utils.di

import com.opalynskyi.utils.api.UtilsFeatureApi
import com.opalynskyi.utils.api.UtilsFeatureDependencies
import dagger.Component

@Component(
    modules = [UtilsModule::class],
    dependencies = [UtilsFeatureDependencies::class],
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
