package com.opalynskyi.movies_popular

import androidx.core.net.toUri
import androidx.navigation.NavController

class MoviesPopularFeatureStarter {
    fun start(navController: NavController) {
        val deepLink = "android-app://com.opalynskyi.PopularMoviesFragment".toUri()
        navController.navigate(deepLink)
    }
}
