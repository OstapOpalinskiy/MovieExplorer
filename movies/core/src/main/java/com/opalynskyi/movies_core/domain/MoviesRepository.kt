package com.opalynskyi.movies_core.domain

import com.opalynskyi.movies_core.domain.entities.Movie
import com.opalynskyi.movies_core.domain.entities.MoviePage
import com.opalynskyi.common.Either
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun observeMovies(): Flow<List<Movie>>
    suspend fun getMovies(startDate: String, endDate: String): Either<Exception, List<Movie>>
    suspend fun getFavourites(): Either<Exception, List<Movie>>
    suspend fun addToFavourites(movie: Movie): Either<Exception, Unit>
    suspend fun removeFromFavourites(movie: Movie): Either<Exception, Boolean>
    fun getMoviesPage(
        pageSize: Int,
        prefetchDistance: Int,
        maxCachedPagesSize: Int
    ): Flow<MoviePage>
}