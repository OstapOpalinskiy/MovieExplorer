package com.opalynskyi.popular.domain

import com.opalynskyi.core.domain.entities.MoviePage
import kotlinx.coroutines.flow.Flow

interface MoviesPopularRepository {
    fun getMoviesPage(
        pageSize: Int,
        prefetchDistance: Int,
        maxCachedPagesSize: Int,
    ): Flow<MoviePage>
}
