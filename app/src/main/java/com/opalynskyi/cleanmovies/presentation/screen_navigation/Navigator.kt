package com.opalynskyi.cleanmovies.presentation.screen_navigation

import androidx.navigation.NavController
import com.opalynskyi.movies_popular.di.MoviesPopularFeatureApi
import javax.inject.Inject
import javax.inject.Provider

class Navigator @Inject constructor(
    private val navigationComponent: NavController,
    private val featureMoviesPopular: Provider<MoviesPopularFeatureApi>
) {
    fun navigate(destination: ScreenDestination) {
        when (destination) {
            is ScreenDestination.Popular -> {
                featureMoviesPopular.get().starter().start(navigationComponent)
            }
        }
    }
}