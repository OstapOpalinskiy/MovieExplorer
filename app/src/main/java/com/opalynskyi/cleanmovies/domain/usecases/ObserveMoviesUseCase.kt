package com.opalynskyi.cleanmovies.domain.usecases

import com.opalynskyi.cleanmovies.DispatcherProvider
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import com.opalynskyi.cleanmovies.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ObserveMoviesUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(): Flow<List<Movie>> {
        return withContext(dispatcherProvider.io()) {
            moviesRepository.observeMovies()
        }
    }
}