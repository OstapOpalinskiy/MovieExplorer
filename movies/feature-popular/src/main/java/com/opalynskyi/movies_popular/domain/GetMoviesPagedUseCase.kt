package com.opalynskyi.movies_popular.domain

import com.opalynskyi.movies_core.domain.entities.MoviePage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesPagedUseCase @Inject constructor(
    private val moviesRepository: MoviesPopularRepository
) {
    operator fun invoke(
        pageSize: Int,
        prefetchDistance: Int,
        maxCachedPagesSize: Int
    ): Flow<MoviePage> {
        return moviesRepository.getMoviesPage(
            pageSize = pageSize,
            prefetchDistance = prefetchDistance,
            maxCachedPagesSize = maxCachedPagesSize
        )
    }
}