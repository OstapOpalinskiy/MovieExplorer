package com.opalynskyi.cleanmovies.app.mainscreen.movies.favourite

import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MovieListMapper
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import dagger.Module
import dagger.Provides

@Module
class FavouriteMoviesModule {

    @Provides
    @MainScreenScope
    fun provideFavouriteMoviesPresenter(
        moviesInteractor: MoviesInteractor,
        scheduler: SchedulerProvider,
        movieListMapper: MovieListMapper
    ): FavouriteMoviesContract.Presenter =
        FavouriteMoviesPresenter(
            moviesInteractor,
            scheduler,
            movieListMapper
        )
}