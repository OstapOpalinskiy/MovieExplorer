package com.opalynskyi.cleanmovies.app.domain.interactors

import com.opalynskyi.cleanmovies.app.domain.entities.Movie
import com.opalynskyi.cleanmovies.app.domain.MovieEvent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface MoviesInteractor {
    fun getMovies(startDate: String, endDate: String): Single<List<Movie>>
    fun getFavourites(): Single<List<Movie>>
    fun addToFavourites(id: Int): Completable
    fun removeFromFavourites(id: Int): Completable
    fun bindEvents(): Observable<MovieEvent>
}