package com.opalynskyi.cleanmovies.app.presentation

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.data.api.MoviesApi
import com.opalynskyi.cleanmovies.app.data.database.MoviesDao
import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.presentation.moviesList.AllMoviesContract
import com.opalynskyi.cleanmovies.app.presentation.moviesList.AllMoviesPresenter
import com.opalynskyi.cleanmovies.app.data.DbMoviesMapper
import com.opalynskyi.cleanmovies.app.data.LocalMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.app.data.RemoteMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.app.data.api.ServerMoviesMapper
import com.opalynskyi.cleanmovies.app.presentation.favourites.FavouriteMoviesContract
import com.opalynskyi.cleanmovies.app.presentation.favourites.FavouriteMoviesPresenter
import com.opalynskyi.cleanmovies.app.SchedulerProvider
import com.opalynskyi.cleanmovies.app.data.LocalMoviesDataSource
import com.opalynskyi.cleanmovies.app.data.MoviesMapper
import com.opalynskyi.cleanmovies.app.data.MoviesRepositoryImpl
import com.opalynskyi.cleanmovies.app.data.RemoteMoviesDataSource
import com.opalynskyi.cleanmovies.app.domain.interactors.MoviesInteractor
import com.opalynskyi.cleanmovies.app.domain.interactors.MoviesInteractorImpl
import com.opalynskyi.cleanmovies.app.domain.MoviesRepository
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