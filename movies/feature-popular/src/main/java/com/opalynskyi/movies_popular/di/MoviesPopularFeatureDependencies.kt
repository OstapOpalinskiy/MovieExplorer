package com.opalynskyi.movies_popular.di

import com.opalynskyi.module_injector.BaseDependencies
import com.opalynskyi.movies_core.domain.usecases.FavouritesUseCases
import com.opalynskyi.movies_core.domain.usecases.GetMoviesPagedUseCase
import com.opalynskyi.movies_list.MovieListMapper
import com.opalynskyi.utils.imageLoader.ImageLoader

interface MoviesPopularFeatureDependencies : BaseDependencies {
    fun imageLoader(): ImageLoader
    fun favouritesUseCases(): FavouritesUseCases
    fun movieListMapper(): MovieListMapper
    fun getMoviesPagedUseCase(): GetMoviesPagedUseCase
}