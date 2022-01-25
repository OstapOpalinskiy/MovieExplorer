package com.com.opalynskyi.favourites.api

import com.opalynskyi.module_injector.BaseDependencies
import com.opalynskyi.movies_core.domain.usecases.FavouritesUseCases
import com.opalynskyi.movies_list.MovieListBuilder
import com.opalynskyi.utils.imageLoader.ImageLoader

interface MoviesFavouriteFeatureDependencies : BaseDependencies {
    fun imageLoader(): ImageLoader
    fun favouritesUseCases(): FavouritesUseCases
    fun movieListMapper(): MovieListBuilder
}