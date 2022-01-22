package com.opalynskyi.movies_core.domain.usecases

import com.opalynskyi.common.DispatcherProvider
import com.opalynskyi.common.Either
import com.opalynskyi.movies_core.domain.MoviesRepository
import com.opalynskyi.movies_core.domain.entities.Movie
import kotlinx.coroutines.withContext

class AddToFavouritesUseCase(
    private val moviesRepository: MoviesRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend operator fun invoke(movie: Movie): Either<Exception, Unit> {
        return withContext(dispatcherProvider.io()) {
            moviesRepository.addToFavourites(movie)
        }
    }
}