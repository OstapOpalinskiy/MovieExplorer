package com.opalynskyi.cleanmovies.app.injection.movies

import com.opalynskyi.cleanmovies.app.movies.MoviesContract
import com.opalynskyi.cleanmovies.app.movies.MoviesPresenter
import com.opalynskyi.cleanmovies.core.domain.user.UserInteractor
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {

    @Provides
    fun provideMoviesPresenter(userInteractor: UserInteractor): MoviesContract.Presenter =
        MoviesPresenter(userInteractor)
}