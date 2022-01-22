package com.opalynskyi.cleanmovies.domain.usecases

import com.opalynskyi.cleanmovies.domain.DispatcherProvider
import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import com.opalynskyi.cleanmovies.domain.entities.Movie
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddToFavouritesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend operator fun invoke(movie: Movie): Either<Exception, Unit> {
        return withContext(dispatcherProvider.io()) {
            moviesRepository.addToFavourites(movie)
        }
    }
}