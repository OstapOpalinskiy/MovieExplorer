package com.opalynskyi.cleanmovies.core.domain.movies

import com.opalynskyi.cleanmovies.core.domain.movies.entities.MovieModel
import io.reactivex.Single

interface MoviesInteractor {
    fun getMovies(startDate: String, endDate: String): Single<List<MovieModel>>
}