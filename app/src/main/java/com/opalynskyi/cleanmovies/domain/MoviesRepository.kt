package com.opalynskyi.cleanmovies.domain

import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.domain.entities.MoviePage
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun observeMovies(): Flow<List<Movie>>
    suspend fun getMovies(startDate: String, endDate: String): Either<Exception, List<Movie>>
    suspend fun getFavourites(): Either<Exception, List<Movie>>
    suspend fun addToFavourites(id: Int): Either<Exception, Boolean>
    suspend fun removeFromFavourites(id: Int): Either<Exception, Boolean>
    fun getMoviesPage(
        pageSize: Int,
        prefetchDistance: Int,
        maxCachedPagesSize: Int
    ): Flow<MoviePage>
}