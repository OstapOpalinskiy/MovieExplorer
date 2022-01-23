package com.opalynskyi.movies_core.di

import com.opalynskyi.movies_core.api.MoviesCoreFeatureApi
import com.opalynskyi.movies_core.api.MoviesCoreFeatureDependencies
import dagger.Component

@Component(
    modules = [MoviesCoreModule::class],
    dependencies = [MoviesCoreFeatureDependencies::class]
)
internal abstract class MoviesCoreComponent: MoviesCoreFeatureApi {
    companion object {
        fun initAndGet(featureDependencies: MoviesCoreFeatureDependencies): MoviesCoreComponent {
            return DaggerMoviesCoreComponent.builder()
                .moviesCoreFeatureDependencies(featureDependencies)
                .build()
        }
    }
}