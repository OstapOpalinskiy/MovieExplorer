package com.opalynskyi.movies_core.domain.usecases

import com.opalynskyi.movies_core.domain.MoviesRepository
import com.opalynskyi.movies_core.domain.entities.MoviePage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesPagedUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
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