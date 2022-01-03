package com.opalynskyi.cleanmovies.presentation

import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.DispatcherProvider
import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.api.ServerMoviesMapper
import com.opalynskyi.cleanmovies.data.database.MoviesDao
import com.opalynskyi.cleanmovies.data.movies.*
import com.opalynskyi.cleanmovies.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import com.opalynskyi.cleanmovies.domain.usecases.AddToFavouritesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.GetFavouritesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.GetMoviesUseCase
import com.opalynskyi.cleanmovies.domain.usecases.RemoveFromFavouritesUseCase
import com.opalynskyi.cleanmovies.presentation.favourites.FavouriteMoviesContract
import com.opalynskyi.cleanmovies.presentation.favourites.FavouriteMoviesPresenter
import com.opalynskyi.cleanmovies.presentation.moviesList.AllMoviesContract
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {
    @Provides
    @MainScreenScope
    fun provideFavouriteMoviesPresenter(
        dispatcherProvider: DispatcherProvider,
        removeFromFavouritesUseCase: RemoveFromFavouritesUseCase,
        getFavouritesUseCase: GetFavouritesUseCase,
        movieListMapper: MovieListMapper,
        dateTimeHelper: DateTimeHelper
    ): FavouriteMoviesContract.Presenter =
        FavouriteMoviesPresenter(
            dispatcherProvider,
            removeFromFavouritesUseCase,
            getFavouritesUseCase,
            movieListMapper,
            dateTimeHelper
        )

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