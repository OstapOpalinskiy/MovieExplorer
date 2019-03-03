package com.opalynskyi.cleanmovies.app.mainscreen.movies

import com.opalynskyi.cleanmovies.app.api.MoviesApi
import com.opalynskyi.cleanmovies.app.db.MoviesDao
import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.DbMoviesMapper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.LocalMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.RemoteMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.ResponseMoviesMapper
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.data.movies.LocalMoviesDataSource
import com.opalynskyi.cleanmovies.core.data.movies.MoviesMapper
import com.opalynskyi.cleanmovies.core.data.movies.MoviesRepositoryImpl
import com.opalynskyi.cleanmovies.core.data.movies.RemoteMoviesDataSource
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractorImpl
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesRepository
import dagger.Module
import dagger.Provides


@Module
class MoviesModule {


    @Provides
    @MainScreenScope
    fun provideMoviesInteractor(
        moviesRepository: MoviesRepository,
        scheduler: SchedulerProvider
    ): MoviesInteractor =
        MoviesInteractorImpl(moviesRepository, scheduler)


    @Provides
    @MainScreenScope
    fun provideMoviesRepository(
        remoteMoviesDataSource: RemoteMoviesDataSource,
        localMoviesDataSource: LocalMoviesDataSource,
        moviesMapper: MoviesMapper
    ): MoviesRepository =
        MoviesRepositoryImpl(remoteMoviesDataSource, localMoviesDataSource, moviesMapper)

    @Provides
    @MainScreenScope
    fun provideRemoteMoviesDataSource(
        api: MoviesApi,
        mapper: ResponseMoviesMapper
    ): RemoteMoviesDataSource =
        RemoteMoviesDataSourceImpl(api, mapper)

    @Provides
    @MainScreenScope
    fun provideResponseMoviesMapper(): ResponseMoviesMapper = ResponseMoviesMapper()

    @Provides
    @MainScreenScope
    fun provideEntityMapper(): MoviesMapper = MoviesMapper()

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

}