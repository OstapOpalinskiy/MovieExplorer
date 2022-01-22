package com.opalynskyi.cleanmovies.presentation.movies

import com.opalynskyi.cleanmovies.data.*
import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.api.ServerMoviesMapper
import com.opalynskyi.cleanmovies.data.database.MoviesDao
import com.opalynskyi.cleanmovies.data.paging.PagingSourceFactory
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
        localMoviesDataSource: LocalMoviesDataSource,
        pagingSource: PagingSourceFactory,
        moviesMapper: DbMoviesMapper
    ): MoviesRepository =
        MoviesRepositoryImpl(
            remoteMoviesDataSource,
            localMoviesDataSource,
            pagingSource,
            moviesMapper
        )

    @Provides
    @MainScreenScope
    fun provideRemoteMoviesDataSource(
        api: MoviesApi,
        mapper: ServerMoviesMapper
    ): RemoteMoviesDataSource =
        RemoteMoviesDataSourceImpl(api, mapper)

    @Provides
    @MainScreenScope
    fun provideResponseMoviesMapper(dateTimeHelper: com.opalynskyi.utils.DateTimeHelper): ServerMoviesMapper =
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
    fun provideMovieListMapper(dateTimeHelper: com.opalynskyi.utils.DateTimeHelper): MovieListMapper =
        MovieListMapper(dateTimeHelper)
}