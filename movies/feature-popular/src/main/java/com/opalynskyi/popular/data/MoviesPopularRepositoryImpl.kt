package com.opalynskyi.popular.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.opalynskyi.core.domain.entities.MoviePage
import com.opalynskyi.popular.PagingDataWrapper
import com.opalynskyi.popular.domain.MoviesPopularRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class MoviesPopularRepositoryImpl(
    private val pagingSourceFactory: PagingSourceFactory,
) : MoviesPopularRepository {
    override fun getMoviesPage(
        pageSize: Int,
        prefetchDistance: Int,
        maxCachedPagesSize: Int,
    ): Flow<MoviePage> {
        return Pager(
            PagingConfig(
                pageSize = pageSize,
                prefetchDistance = prefetchDistance,
                maxSize = maxCachedPagesSize,
            ),
        ) {
            pagingSourceFactory.newInstance()
        }.flow.map { PagingDataWrapper(it) }
    }
}
