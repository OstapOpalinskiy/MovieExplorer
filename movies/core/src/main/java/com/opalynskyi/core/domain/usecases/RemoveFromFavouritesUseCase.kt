package com.opalynskyi.core.domain.usecases

import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.common.Either
import com.opalynskyi.core.domain.MoviesRepository
import com.opalynskyi.core.domain.entities.Movie
import kotlinx.coroutines.withContext

class RemoveFromFavouritesUseCase(
    private val moviesRepository: MoviesRepository,
    private val dispatcherProvider: DispatcherProvider,
) {
    suspend operator fun invoke(movie: Movie): Either<Exception, Boolean> {
        return withContext(dispatcherProvider.io()) {
            moviesRepository.removeFromFavourites(movie)
        }
    }
}
