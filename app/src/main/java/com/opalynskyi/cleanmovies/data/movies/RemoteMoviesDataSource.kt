package com.opalynskyi.cleanmovies.data.movies

import com.opalynskyi.cleanmovies.Either
import com.opalynskyi.cleanmovies.domain.entities.Movie
import io.reactivex.Single

interface RemoteMoviesDataSource {
    fun getMovies(startDate: String, endDate: String): Single<List<Movie>>
    suspend fun getMoviesEither(startDate: String, endDate: String): Either<Exception, List<Movie>>
}