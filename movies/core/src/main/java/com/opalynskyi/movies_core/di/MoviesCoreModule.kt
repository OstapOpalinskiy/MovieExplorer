package com.opalynskyi.movies_core.di

import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.movies_core.domain.MoviesRepository
import com.opalynskyi.movies_core.domain.usecases.*
import dagger.Module
import dagger.Provides

@Module
internal class MoviesCoreModule {
    @Provides
    fun provideFavouritesUseCases(
        dispatcherProvider: DispatcherProvider,
        moviesRepository: MoviesRepository
    ): FavouritesUseCases {
        return FavouritesUseCases(
            AddToFavouritesUseCase(moviesRepository, dispatcherProvider),
            RemoveFromFavouritesUseCase(moviesRepository, dispatcherProvider),
            ObserveFavouriteMoviesUseCase(moviesRepository)
        )
    }

    @Provides
    fun provideGetPagedMoviesUseCase(
        moviesRepository: MoviesRepository
    ): GetMoviesPagedUseCase {
        return GetMoviesPagedUseCase(moviesRepository)
    }
}