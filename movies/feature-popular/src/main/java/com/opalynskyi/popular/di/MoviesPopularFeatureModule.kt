package com.opalynskyi.popular.di

import com.opalynskyi.network.api.MoviesApi
import com.opalynskyi.network.api.ServerMoviesMapper
import com.opalynskyi.popular.MoviesPopularFeatureStarter
import com.opalynskyi.popular.data.MoviesPagingSource
import com.opalynskyi.popular.data.MoviesPopularRepositoryImpl
import com.opalynskyi.popular.data.PagingSourceFactory
import com.opalynskyi.popular.domain.MoviesPopularRepository
import dagger.Module
import dagger.Provides

@Module
internal class MoviesPopularFeatureModule {
    @Provides
    fun providesFeatureStarter(): MoviesPopularFeatureStarter {
        return MoviesPopularFeatureStarter()
    }

    @Provides
    fun providesMoviesRepository(pagingSourceFactory: PagingSourceFactory): MoviesPopularRepository {
        return MoviesPopularRepositoryImpl(pagingSourceFactory)
    }

    @Provides
    fun providesMoviesPagingSourceFactory(
        moviesApi: MoviesApi,
        mapper: ServerMoviesMapper,
    ): PagingSourceFactory {
        return PagingSourceFactory(moviesApi, mapper)
    }

    @Provides
    fun providesMoviesPagingSource(
        moviesApi: MoviesApi,
        mapper: ServerMoviesMapper,
    ): MoviesPagingSource {
        return MoviesPagingSource(moviesApi, mapper)
    }
}
