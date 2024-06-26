package com.opalynskyi.popular.api

import com.opalynskyi.core.domain.usecases.FavouritesUseCases
import com.opalynskyi.injector.BaseDependencies
import com.opalynskyi.movies.MovieListBuilder
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
