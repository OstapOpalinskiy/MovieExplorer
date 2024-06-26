package com.opalynskyi.core.di

import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.core.data.DbMoviesMapper
import com.opalynskyi.core.data.LocalMoviesDataSource
import com.opalynskyi.core.data.LocalMoviesDataSourceImpl
import com.opalynskyi.core.data.MoviesRepositoryImpl
import com.opalynskyi.core.domain.MoviesRepository
import com.opalynskyi.core.domain.usecases.*
import com.opalynskyi.db.MoviesDao
import com.opalynskyi.db.MoviesDatabase
import dagger.Module
import dagger.Provides

@Module
internal class MoviesCoreModule {
    @Provides
    fun provideFavouritesUseCases(
        dispatcherProvider: DispatcherProvider,
        moviesRepository: MoviesRepository,
    ): FavouritesUseCases {
        return FavouritesUseCases(
            AddToFavouritesUseCase(moviesRepository, dispatcherProvider),
            RemoveFromFavouritesUseCase(moviesRepository, dispatcherProvider),
            ObserveFavouriteMoviesUseCase(moviesRepository),
        )
    }

    @Provides
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao = moviesDatabase.moviesDao()

    @Provides
    fun provideMoviesRepository(
        localMoviesDataSource: LocalMoviesDataSource,
        moviesMapper: DbMoviesMapper,
    ): MoviesRepository =
        MoviesRepositoryImpl(
            localMoviesDataSource,
            moviesMapper,
        )

    @Provides
    fun provideLocalMoviesDataSource(
        dao: MoviesDao,
        mapper: DbMoviesMapper,
    ): LocalMoviesDataSource {
        return LocalMoviesDataSourceImpl(dao, mapper)
    }

    @Provides
    fun provideDbMoviesMapper(): DbMoviesMapper = DbMoviesMapper()
}
