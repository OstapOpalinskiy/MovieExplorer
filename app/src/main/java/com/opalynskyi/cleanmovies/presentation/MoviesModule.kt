package com.opalynskyi.cleanmovies.presentation

import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.DispatcherProvider
import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.database.MoviesDao
import com.opalynskyi.cleanmovies.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.presentation.moviesList.AllMoviesContract
import com.opalynskyi.cleanmovies.presentation.moviesList.AllMoviesPresenter
import com.opalynskyi.cleanmovies.data.movies.DbMoviesMapper
import com.opalynskyi.cleanmovies.data.movies.LocalMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.data.movies.RemoteMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.data.api.ServerMoviesMapper
import com.opalynskyi.cleanmovies.presentation.favourites.FavouriteMoviesContract
import com.opalynskyi.cleanmovies.presentation.favourites.FavouriteMoviesPresenter
import com.opalynskyi.cleanmovies.SchedulerProvider
import com.opalynskyi.cleanmovies.data.movies.LocalMoviesDataSource
import com.opalynskyi.cleanmovies.data.movies.MoviesRepositoryImpl
import com.opalynskyi.cleanmovies.data.movies.RemoteMoviesDataSource
import com.opalynskyi.cleanmovies.domain.interactors.MoviesInteractor
import com.opalynskyi.cleanmovies.domain.interactors.MoviesInteractorImpl
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import com.opalynskyi.cleanmovies.domain.usecases.AddToFavouritesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.GetMoviesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.RemoveFromFavouritesUseCase
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
        dispatcherProvider: DispatcherProvider,
        getMoviesUseCase: GetMoviesUseCase,
        addToFavouritesUseCase: AddToFavouritesUseCase,
        removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
        movieListMapper: MovieListMapper,
        dateTimeHelper: DateTimeHelper
    ): AllMoviesContract.Presenter =
        AllMoviesPresenter(
            dispatcherProvider,
            getMoviesUseCase,
            addToFavouritesUseCase,
            removeFromFavouritesUseCase,
            dateTimeHelper,
            movieListMapper,
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