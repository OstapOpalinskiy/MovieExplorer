package com.opalynskyi.cleanmovies.domain.usecases

import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import com.opalynskyi.cleanmovies.domain.entities.Movie
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(): Either<Exception, List<Movie>> {
        return moviesRepository.getFavourites()
    }
}