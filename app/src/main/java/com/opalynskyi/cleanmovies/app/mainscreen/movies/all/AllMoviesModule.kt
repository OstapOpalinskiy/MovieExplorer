package com.opalynskyi.cleanmovies.app.mainscreen.movies.all

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.mainscreen.movies.MovieListMapper
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import dagger.Module
import dagger.Provides

@Module
class AllMoviesModule {

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
}