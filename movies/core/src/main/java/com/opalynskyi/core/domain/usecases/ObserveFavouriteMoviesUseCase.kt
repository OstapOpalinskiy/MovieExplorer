package com.opalynskyi.core.domain.usecases

import com.opalynskyi.core.domain.MoviesRepository
import com.opalynskyi.core.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class ObserveFavouriteMoviesUseCase(
    private val moviesRepository: MoviesRepository,
) {
    suspend operator fun invoke(): Flow<List<Movie>> {
        return moviesRepository.observeFavouriteMovies()
    }
}
