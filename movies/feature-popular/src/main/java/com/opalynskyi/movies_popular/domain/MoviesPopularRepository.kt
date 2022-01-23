package com.opalynskyi.movies_popular.domain

import com.opalynskyi.movies_core.domain.entities.MoviePage
import kotlinx.coroutines.flow.Flow

interface MoviesPopularRepository {
    fun getMoviesPage(
        pageSize: Int,
        prefetchDistance: Int,
        maxCachedPagesSize: Int
    ): Flow<MoviePage>
}