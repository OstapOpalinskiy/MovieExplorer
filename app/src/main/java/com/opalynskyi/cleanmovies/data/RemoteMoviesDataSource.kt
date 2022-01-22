package com.opalynskyi.cleanmovies.data

import com.opalynskyi.common.Either
import com.opalynskyi.movies_core.domain.entities.Movie

interface RemoteMoviesDataSource {
    suspend fun getMovies(startDate: String, endDate: String): Either<Exception, List<Movie>>
    suspend fun getMoviesPaged(page: Int): Either<Exception, List<Movie>>
}