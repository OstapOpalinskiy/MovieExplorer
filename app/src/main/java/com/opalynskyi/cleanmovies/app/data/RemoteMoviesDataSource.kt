package com.opalynskyi.cleanmovies.app.data

import com.opalynskyi.cleanmovies.app.domain.entities.Movie
import io.reactivex.Single

interface RemoteMoviesDataSource {
    fun getMovies(startDate: String, endDate: String): Single<List<Movie>>
}