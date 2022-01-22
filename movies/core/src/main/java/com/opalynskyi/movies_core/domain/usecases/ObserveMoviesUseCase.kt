package com.opalynskyi.movies_core.domain.usecases

import com.opalynskyi.movies_core.domain.MoviesRepository
import com.opalynskyi.movies_core.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(): Flow<List<Movie>> {
        return moviesRepository.observeMovies()
    }
}