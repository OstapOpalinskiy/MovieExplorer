package com.opalynskyi.movies_popular.di

import com.opalynskyi.movies_popular.MoviesPopularFeatureStarter
import dagger.Module
import dagger.Provides

@Module
internal class MoviesPopularFeatureModule {
    @Provides
    fun providesFeatureStarter(): MoviesPopularFeatureStarter {
        return MoviesPopularFeatureStarter()
    }
}