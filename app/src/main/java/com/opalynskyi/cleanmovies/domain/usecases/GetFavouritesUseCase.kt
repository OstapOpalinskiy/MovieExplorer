package com.opalynskyi.cleanmovies.domain.usecases

import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.common.Either
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import com.opalynskyi.cleanmovies.domain.entities.Movie
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend operator fun invoke(): Either<Exception, List<Movie>> {
        return withContext(dispatcherProvider.io()) {
            moviesRepository.getFavourites()
        }
    }
}