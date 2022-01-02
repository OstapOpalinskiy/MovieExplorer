package com.opalynskyi.cleanmovies.domain.usecases

import com.opalynskyi.cleanmovies.DispatcherProvider
import com.opalynskyi.cleanmovies.Either
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import com.opalynskyi.cleanmovies.domain.entities.Movie
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
            moviesRepository.getMoviesEither(startDate, endDate)
        }
    }
}