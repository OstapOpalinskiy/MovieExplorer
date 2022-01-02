package com.opalynskyi.cleanmovies.domain

import com.opalynskyi.cleanmovies.Either
import com.opalynskyi.cleanmovies.domain.entities.Movie
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MoviesRepository {
    fun getMovies(startDate: String, endDate: String): Single<List<Movie>>
    fun getFavourites(): Single<List<Movie>>
    fun addToFavourites(id: Int): Completable
    fun removeFromFavourites(id: Int): Completable

    suspend fun getMoviesEither(startDate: String, endDate: String): Either<Exception, List<Movie>>
    suspend fun getFavouritesEither(): Either<Exception, List<Movie>>
    suspend fun addToFavouritesEither(id: Int): Either<Exception, Boolean>
    suspend fun removeFromFavouritesEither(id: Int): Either<Exception, Boolean>

    fun bindEvents(): Observable<MovieEvent>
}