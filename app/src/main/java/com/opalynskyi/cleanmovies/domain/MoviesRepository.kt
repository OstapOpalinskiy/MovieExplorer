package com.opalynskyi.cleanmovies.domain

import com.opalynskyi.cleanmovies.Either
import com.opalynskyi.cleanmovies.domain.entities.Movie

interface MoviesRepository {
    suspend fun getMoviesEither(startDate: String, endDate: String): Either<Exception, List<Movie>>
    suspend fun getFavouritesEither(): Either<Exception, List<Movie>>
    suspend fun addToFavouritesEither(id: Int): Either<Exception, Boolean>
    suspend fun removeFromFavouritesEither(id: Int): Either<Exception, Boolean>
}