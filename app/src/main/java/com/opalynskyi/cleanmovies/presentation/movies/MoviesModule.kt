package com.opalynskyi.cleanmovies.presentation.movies

import com.opalynskyi.cleanmovies.data.*
import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.api.ServerMoviesMapper
import com.opalynskyi.cleanmovies.data.database.MoviesDao
import com.opalynskyi.cleanmovies.data.paging.PagingSourceFactory
import com.opalynskyi.cleanmovies.di.scopes.MainScreenScope
import com.opalynskyi.movies_core.domain.MoviesRepository
import com.opalynskyi.movies_list.MovieListMapper
import com.opalynskyi.utils.DateTimeHelper
import dagger.Module
import dagger.Provides

@Module
class MoviesModule