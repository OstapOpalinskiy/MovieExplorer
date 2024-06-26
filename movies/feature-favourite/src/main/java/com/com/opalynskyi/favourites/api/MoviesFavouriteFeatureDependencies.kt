package com.com.opalynskyi.favourites.api

import com.opalynskyi.core.domain.usecases.FavouritesUseCases
import com.opalynskyi.injector.BaseDependencies
import com.opalynskyi.movies.MovieListBuilder
import com.opalynskyi.utils.imageLoader.ImageLoader

interface MoviesFavouriteFeatureDependencies : BaseDependencies {
    fun imageLoader(): ImageLoader

    fun favouritesUseCases(): FavouritesUseCases

    fun movieListMapper(): MovieListBuilder
}
