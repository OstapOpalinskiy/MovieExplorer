package com.opalynskyi.core.domain

import com.opalynskyi.common.Either
import com.opalynskyi.core.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun observeFavouriteMovies(): Flow<List<Movie>>

    suspend fun getFavourites(): Either<Exception, List<Movie>>

    suspend fun addToFavourites(movie: Movie): Either<Exception, Unit>

    suspend fun removeFromFavourites(movie: Movie): Either<Exception, Boolean>
}
