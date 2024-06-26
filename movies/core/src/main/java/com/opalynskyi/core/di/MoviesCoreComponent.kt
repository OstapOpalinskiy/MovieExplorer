package com.opalynskyi.core.di

import com.opalynskyi.core.api.MoviesCoreFeatureApi
import com.opalynskyi.core.api.MoviesCoreFeatureDependencies
import dagger.Component

@Component(
    modules = [MoviesCoreModule::class],
    dependencies = [MoviesCoreFeatureDependencies::class],
)
internal abstract class MoviesCoreComponent : MoviesCoreFeatureApi {
    companion object {
        fun initAndGet(featureDependencies: MoviesCoreFeatureDependencies): MoviesCoreComponent {
            return DaggerMoviesCoreComponent.builder()
                .moviesCoreFeatureDependencies(featureDependencies)
                .build()
        }
    }
}
