package com.opalynskyi.movies_popular.di

import com.opalynskyi.movies_popular.MoviesPopularFeatureStarter
import com.opalynskyi.movies_popular.data.MoviesPagingSource
import com.opalynskyi.movies_popular.data.MoviesPopularRepositoryImpl
import com.opalynskyi.movies_popular.data.PagingSourceFactory
import com.opalynskyi.movies_popular.domain.MoviesPopularRepository
import com.opalynskyi.network.api.MoviesApi
import com.opalynskyi.network.api.ServerMoviesMapper
import dagger.Module
import dagger.Provides

@Module
internal class MoviesPopularFeatureModule {
    @Provides
    fun providesFeatureStarter(): MoviesPopularFeatureStarter {
        return MoviesPopularFeatureStarter()
    }

    @Provides
    fun providesMoviesRepository(
        pagingSourceFactory: PagingSourceFactory
    ): MoviesPopularRepository {
        return MoviesPopularRepositoryImpl(pagingSourceFactory)
    }

    @Provides
    fun providesMoviesPagingSourceFactory(
        moviesApi: MoviesApi,
        mapper: ServerMoviesMapper
    ): PagingSourceFactory {
        return PagingSourceFactory(moviesApi, mapper)
    }

    @Provides
    fun providesMoviesPagingSource(
        moviesApi: MoviesApi,
        mapper: ServerMoviesMapper
    ): MoviesPagingSource {
        return MoviesPagingSource(moviesApi, mapper)
    }
}