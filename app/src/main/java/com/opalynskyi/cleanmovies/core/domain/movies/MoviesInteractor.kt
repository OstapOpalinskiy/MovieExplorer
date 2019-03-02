package com.opalynskyi.cleanmovies.core.domain.movies

import com.opalynskyi.cleanmovies.core.domain.movies.entities.Movie
import io.reactivex.Completable
import io.reactivex.Single

interface MoviesInteractor {
    fun getMovies(startDate: String, endDate: String): Single<List<Movie>>
    fun getFavourites(): Single<List<Movie>>
    fun addToFavourites(movie: Movie): Completable
    fun removeFromFavourites(movie: Movie): Completable
}