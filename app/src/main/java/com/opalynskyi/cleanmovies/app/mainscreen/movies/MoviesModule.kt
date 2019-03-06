package com.opalynskyi.cleanmovies.app.mainscreen.movies

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.api.MoviesApi
import com.opalynskyi.cleanmovies.app.database.MoviesDao
import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.mainscreen.movies.all.AllMoviesContract
import com.opalynskyi.cleanmovies.app.mainscreen.movies.all.AllMoviesPresenter
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.DbMoviesMapper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.LocalMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.RemoteMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.ServerMoviesMapper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite.FavouriteMoviesContract
import com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite.FavouriteMoviesPresenter
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.movies.data.LocalMoviesDataSource
import com.opalynskyi.cleanmovies.core.movies.data.MoviesMapper
import com.opalynskyi.cleanmovies.core.movies.data.MoviesRepositoryImpl
import com.opalynskyi.cleanmovies.core.movies.data.RemoteMoviesDataSource
import com.opalynskyi.cleanmovies.core.movies.domain.MoviesInteractor
import com.opalynskyi.cleanmovies.core.movies.domain.MoviesInteractorImpl
import com.opalynskyi.cleanmovies.core.movies.domain.MoviesRepository
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {
    @Provides
    @MainScreenScope
    fun provideFavouriteMoviesPresenter(
        moviesInteractor: MoviesInteractor,
        scheduler: SchedulerProvider,
        movieListMapper: MovieListMapper,
        dateTimeHelper: DateTimeHelper
    ): FavouriteMoviesContract.Presenter =
        FavouriteMoviesPresenter(
            moviesInteractor,
            scheduler,
            movieListMapper,
            dateTimeHelper
        )

    @Provides
    @MainScreenScope
    fun provideAllMoviesPresenter(
        moviesInteractor: MoviesInteractor,
        scheduler: SchedulerProvider,
        movieListMapper: MovieListMapper,
        dateTimeHelper: DateTimeHelper
    ): AllMoviesContract.Presenter =
        AllMoviesPresenter(
            moviesInteractor,
            scheduler,
            movieListMapper,
            dateTimeHelper
        )

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
        MoviesRepositoryImpl(
            remoteMoviesDataSource,
            localMoviesDataSource,
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
    fun provideResponseMoviesMapper(dateTimeHelper: DateTimeHelper): ServerMoviesMapper =
        ServerMoviesMapper(dateTimeHelper)

    @Provides
    @MainScreenScope
    fun provideEntityMapper(): MoviesMapper =
        MoviesMapper()

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
    fun provideMovieListMapper(dateTimeHelper: DateTimeHelper): MovieListMapper = MovieListMapper(dateTimeHelper)
}