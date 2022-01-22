package com.opalynskyi.movies_core.domain.usecases

import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.common.Either
import com.opalynskyi.movies_core.domain.MoviesRepository
import com.opalynskyi.movies_core.domain.entities.Movie
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(
        startDate: String,
        endDate: String
    ): Either<Exception, List<Movie>> {
        return withContext(dispatcherProvider.io()) {
            moviesRepository.getMovies(startDate, endDate)
        }
    }
}