package com.opalynskyi.cleanmovies.data

import com.opalynskyi.common.Either
import com.opalynskyi.cleanmovies.domain.entities.Movie

interface RemoteMoviesDataSource {
    suspend fun getMovies(startDate: String, endDate: String): Either<Exception, List<Movie>>
    suspend fun getMoviesPaged(page: Int): Either<Exception, List<Movie>>
}