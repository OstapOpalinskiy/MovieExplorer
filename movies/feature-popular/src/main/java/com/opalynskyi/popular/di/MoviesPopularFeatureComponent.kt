package com.opalynskyi.popular.di

import com.opalynskyi.popular.PopularMoviesFragment
import com.opalynskyi.popular.api.MoviesPopularFeatureApi
import com.opalynskyi.popular.api.MoviesPopularFeatureDependencies
import dagger.Component

@Component(
    dependencies = [MoviesPopularFeatureDependencies::class],
    modules = [MoviesPopularFeatureModule::class],
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
