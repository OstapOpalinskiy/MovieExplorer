package com.opalynskyi.movie_explorer.presentation

import androidx.navigation.NavController
import com.com.opalynskyi.favourites.api.MoviesFavouriteFeatureApi
import com.opalynskyi.movies_popular.api.MoviesPopularFeatureApi
import javax.inject.Inject
import javax.inject.Provider

class Navigator @Inject constructor(
    private val navigationComponent: NavController,
    private val featurePopular: Provider<MoviesPopularFeatureApi>,
    private val featureFavourite: Provider<MoviesFavouriteFeatureApi>,
) {
    fun navigate(destination: ScreenDestination) {
        when (destination) {
            is ScreenDestination.Popular -> {
                featurePopular.get().starter().start(navigationComponent)
            }
            is ScreenDestination.Favourite -> {
                featureFavourite.get().starter().start(navigationComponent)
            }
        }
    }
}