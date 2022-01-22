package com.opalynskyi.cleanmovies.domain.usecases

import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.common.Either
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import com.opalynskyi.cleanmovies.domain.entities.Movie
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveFromFavouritesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend operator fun invoke(movie: Movie): Either<Exception, Boolean> {
        return withContext(dispatcherProvider.io()) {
            moviesRepository.removeFromFavourites(movie)
        }
    }
}