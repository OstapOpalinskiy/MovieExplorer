package com.opalynskyi.cleanmovies.data.movies

import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.entities.Movie

interface RemoteMoviesDataSource {
    suspend fun getMoviesEither(startDate: String, endDate: String): Either<Exception, List<Movie>>
}