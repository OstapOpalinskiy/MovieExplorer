package com.opalynskyi.cleanmovies.core.movies.data

import com.opalynskyi.cleanmovies.core.movies.data.entities.MovieEntity
import io.reactivex.Single

interface RemoteMoviesDataSource {
    fun getMovies(startDate: String, endDate: String): Single<List<MovieEntity>>
}