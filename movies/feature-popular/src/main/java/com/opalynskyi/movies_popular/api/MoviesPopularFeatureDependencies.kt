package com.opalynskyi.movies_popular.api

import com.opalynskyi.module_injector.BaseDependencies
import com.opalynskyi.movies_core.domain.usecases.FavouritesUseCases
import com.opalynskyi.movies_list.MovieListBuilder
import com.opalynskyi.network.api.MoviesApi
import com.opalynskyi.network.api.ServerMoviesMapper
import com.opalynskyi.utils.imageLoader.ImageLoader

interface MoviesPopularFeatureDependencies : BaseDependencies {
    fun imageLoader(): ImageLoader
    fun favouritesUseCases(): FavouritesUseCases
    fun movieListMapper(): MovieListBuilder
    fun moviesApi(): MoviesApi
    fun serverMoviesMapper(): ServerMoviesMapper
}