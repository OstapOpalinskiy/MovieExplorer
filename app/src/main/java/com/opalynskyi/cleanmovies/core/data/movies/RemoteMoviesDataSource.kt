package com.opalynskyi.cleanmovies.core.data.movies

import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity
import io.reactivex.Single

interface RemoteMoviesDataSource {
    fun getMovies(startDate: String, endDate: String): Single<List<MovieEntity>>
}