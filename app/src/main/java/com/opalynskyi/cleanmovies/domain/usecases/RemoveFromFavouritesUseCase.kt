package com.opalynskyi.cleanmovies.domain.usecases

import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import javax.inject.Inject

class RemoveFromFavouritesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(id: Int): Either<Exception, Boolean> {
        return moviesRepository.removeFromFavourites(id)
    }
}