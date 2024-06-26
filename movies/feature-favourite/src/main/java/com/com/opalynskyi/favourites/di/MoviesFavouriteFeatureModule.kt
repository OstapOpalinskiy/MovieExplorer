package com.com.opalynskyi.favourites.di

import com.com.opalynskyi.favourites.MoviesFavouriteFeatureStarter
import dagger.Module
import dagger.Provides

@Module
internal class MoviesFavouriteFeatureModule {
    @Provides
    fun providesFeatureStarter(): MoviesFavouriteFeatureStarter {
        return MoviesFavouriteFeatureStarter()
    }
}
