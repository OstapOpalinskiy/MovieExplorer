package com.com.opalynskyi.favourites.di

import com.com.opalynskyi.favourites.FavouriteMoviesFragment
import com.com.opalynskyi.favourites.api.MoviesFavouriteFeatureApi
import com.com.opalynskyi.favourites.api.MoviesFavouriteFeatureDependencies
import dagger.Component

@Component(
    dependencies = [MoviesFavouriteFeatureDependencies::class],
    modules = [MoviesFavouriteFeatureModule::class],
)
internal abstract class MoviesFavouriteFeatureComponent : MoviesFavouriteFeatureApi {
    abstract fun inject(FavouriteMoviesFragment: FavouriteMoviesFragment)

    companion object {
        fun initAndGet(featureDependencies: MoviesFavouriteFeatureDependencies): MoviesFavouriteFeatureComponent {
            return DaggerMoviesFavouriteFeatureComponent.builder()
                .moviesFavouriteFeatureDependencies(featureDependencies)
                .build()
        }
    }
}
