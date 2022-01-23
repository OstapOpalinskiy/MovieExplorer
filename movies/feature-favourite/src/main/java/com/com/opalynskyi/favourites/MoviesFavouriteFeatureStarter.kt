package com.com.opalynskyi.favourites

import androidx.core.net.toUri
import androidx.navigation.NavController

class MoviesFavouriteFeatureStarter {
    fun start(navController: NavController) {
        val deepLink = "android-app://com.opalynskyi.FavouriteMoviesFragment".toUri()
        navController.navigate(deepLink)
    }
}
