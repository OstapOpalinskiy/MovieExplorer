package com.opalynskyi.cleanmovies.presentation

import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.api.ServerMoviesMapper
import com.opalynskyi.cleanmovies.data.database.MoviesDao
import com.opalynskyi.cleanmovies.data.movies.RemoteMoviesDataSource
import com.opalynskyi.cleanmovies.data.movies.LocalMoviesDataSource
import com.opalynskyi.cleanmovies.data.movies.MoviesRepositoryImpl
import com.opalynskyi.cleanmovies.data.movies.RemoteMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.data.movies.LocalMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.data.movies.DbMoviesMapper
import com.opalynskyi.cleanmovies.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {

    @Provides
    @MainScreenScope
    fun provideMoviesRepository(
        remoteMoviesDataSource: RemoteMoviesDataSource,
        localMoviesDataSource: LocalMoviesDataSource
    ): MoviesRepository =
        MoviesRepositoryImpl(remoteMoviesDataSource, localMoviesDataSource)

    @Provides
    @MainScreenScope
    fun provideRemoteMoviesDataSource(
        api: MoviesApi,
        mapper: ServerMoviesMapper
    ): RemoteMoviesDataSource =
        RemoteMoviesDataSourceImpl(api, mapper)

    @Provides
    @MainScreenScope
    fun provideResponseMoviesMapper(dateTimeHelper: DateTimeHelper): ServerMoviesMapper =
        ServerMoviesMapper(dateTimeHelper)

    @Provides
    @MainScreenScope
    fun provideLocalMoviesDataSource(
        dao: MoviesDao,
        mapper: DbMoviesMapper
    ): LocalMoviesDataSource {
        return LocalMoviesDataSourceImpl(dao, mapper)
    }

    @Provides
    @MainScreenScope
    fun provideDbMoviesMapper(): DbMoviesMapper = DbMoviesMapper()

    @Provides
    @MainScreenScope
    fun provideMovieListMapper(dateTimeHelper: DateTimeHelper): MovieListMapper =
        MovieListMapper(dateTimeHelper)
}