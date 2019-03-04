package com.opalynskyi.cleanmovies.core.domain.movies

import com.opalynskyi.cleanmovies.core.domain.movies.entities.Movie
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MoviesRepository {
    fun getMovies(startDate: String, endDate: String): Single<List<Movie>>
    fun getFavourites(): Single<List<Movie>>
    fun addToFavourites(id: Int): Completable
    fun removeFromFavourites(id: Int): Completable
    fun bindEvents(): Observable<MovieEvent>
}