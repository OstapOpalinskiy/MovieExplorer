package com.opalynskyi.movies_popular.di

import com.opalynskyi.movies_popular.PopularMoviesFragment
import dagger.Component

@Component(
    dependencies = [MoviesPopularFeatureDependencies::class],
    modules = [MoviesPopularFeatureModule::class]
)
internal abstract class MoviesPopularFeatureComponent : MoviesPopularFeatureApi {
    abstract fun inject(popularMoviesFragment: PopularMoviesFragment)

    companion object {
        fun initAndGet(featureDependencies: MoviesPopularFeatureDependencies): MoviesPopularFeatureComponent {
            return DaggerMoviesPopularFeatureComponent.builder()
                .moviesPopularFeatureDependencies(featureDependencies)
                .build()
        }
    }
}