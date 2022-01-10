package com.opalynskyi.cleanmovies.domain.usecases

import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import javax.inject.Inject

class AddToFavouritesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(id: Int): Either<Exception, Boolean> {
        return moviesRepository.addToFavourites(id)
    }
}