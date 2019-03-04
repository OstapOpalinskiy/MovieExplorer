package com.opalynskyi.cleanmovies.app.mainscreen.movies

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.api.MoviesApi
import com.opalynskyi.cleanmovies.app.database.MoviesDao
import com.opalynskyi.cleanmovies.app.di.scopes.MainScreenScope
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.DbMoviesMapper
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.LocalMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.RemoteMoviesDataSourceImpl
import com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource.ServerMoviesMapper
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




}