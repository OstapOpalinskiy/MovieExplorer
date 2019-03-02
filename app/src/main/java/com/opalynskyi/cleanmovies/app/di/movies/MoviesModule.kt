package com.opalynskyi.cleanmovies.app.di.movies

import com.opalynskyi.cleanmovies.app.api.MoviesApi
import com.opalynskyi.cleanmovies.app.db.MoviesDao
import com.opalynskyi.cleanmovies.app.di.scopes.MoviesActivityScope
import com.opalynskyi.cleanmovies.app.movies.MoviesContract
import com.opalynskyi.cleanmovies.app.movies.MoviesPresenter
import com.opalynskyi.cleanmovies.app.movies.datasource.DbMoviesMapper
import com.opalynskyi.cleanmovies.app.movies.datasource.LocalMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.app.movies.datasource.RemoteMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.app.movies.datasource.ResponseMoviesMapper
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.data.movies.LocalMoviesDataSource
import com.opalynskyi.cleanmovies.core.data.movies.MoviesMapper
import com.opalynskyi.cleanmovies.core.data.movies.MoviesRepositoryImpl
import com.opalynskyi.cleanmovies.core.data.movies.RemoteMoviesDataSource
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractorImpl
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesRepository
import com.opalynskyi.cleanmovies.core.domain.user.UserInteractor
import dagger.Module
import dagger.Provides


@Module
class MoviesModule {

    @Provides
    @MoviesActivityScope
    fun provideMoviesPresenter(
        userInteractor: UserInteractor,
        moviesInteractor: MoviesInteractor
    ): MoviesContract.Presenter =
        MoviesPresenter(userInteractor, moviesInteractor)

    @Provides
    @MoviesActivityScope
    fun provideMoviesInteractor(
        moviesRepository: MoviesRepository,
        scheduler: SchedulerProvider
    ): MoviesInteractor =
        MoviesInteractorImpl(moviesRepository, scheduler)


    @Provides
    @MoviesActivityScope
    fun provideMoviesRepository(
        remoteMoviesDataSource: RemoteMoviesDataSource,
        localMoviesDataSource: LocalMoviesDataSource,
        moviesMapper: MoviesMapper
    ): MoviesRepository =
        MoviesRepositoryImpl(remoteMoviesDataSource, localMoviesDataSource, moviesMapper)

    @Provides
    @MoviesActivityScope
    fun provideRemoteMoviesDataSource(
        api: MoviesApi,
        mapper: ResponseMoviesMapper
    ): RemoteMoviesDataSource =
        RemoteMoviesDataSourceImpl(api, mapper)

    @Provides
    @MoviesActivityScope
    fun provideResponseMoviesMapper(): ResponseMoviesMapper = ResponseMoviesMapper()

    @Provides
    @MoviesActivityScope
    fun provideEntityMapper(): MoviesMapper = MoviesMapper()

    @Provides
    @MoviesActivityScope
    fun provideLocalMoviesDataSource(
        dao: MoviesDao,
        mapper: DbMoviesMapper

    ): LocalMoviesDataSource {
        return LocalMoviesDataSourceImpl(dao, mapper)
    }

    @Provides
    @MoviesActivityScope
    fun provideDbMoviesMapper(): DbMoviesMapper = DbMoviesMapper()

}