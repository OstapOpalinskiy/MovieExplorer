package com.opalynskyi.cleanmovies.domain.usecases

import com.opalynskyi.cleanmovies.DispatcherProvider
import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveFromFavouritesUseCase @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(id: Int): Either<Exception, Boolean> {
        return withContext(dispatcherProvider.io()) {
            moviesRepository.removeFromFavouritesEither(id)
        }
    }
}