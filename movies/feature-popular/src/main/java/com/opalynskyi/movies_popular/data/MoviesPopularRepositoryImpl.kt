package com.opalynskyi.movies_popular.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.opalynskyi.movies_core.domain.entities.MoviePage
import com.opalynskyi.movies_popular.PagingDataWrapper
import com.opalynskyi.movies_popular.domain.MoviesPopularRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class MoviesPopularRepositoryImpl(
    private val pagingSourceFactory: PagingSourceFactory,
) : MoviesPopularRepository {

    override fun getMoviesPage(
        pageSize: Int,
        prefetchDistance: Int,
        maxCachedPagesSize: Int
    ): Flow<MoviePage> {
        return Pager(
            PagingConfig(
                pageSize = pageSize,
                prefetchDistance = prefetchDistance,
                maxSize = maxCachedPagesSize
            )
        ) {
            pagingSourceFactory.newInstance()
        }.flow.map { PagingDataWrapper(it) }
    }
}