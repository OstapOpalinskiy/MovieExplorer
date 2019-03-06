package com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource

import com.opalynskyi.cleanmovies.app.api.MoviesApi
import com.opalynskyi.cleanmovies.core.movies.data.RemoteMoviesDataSource
import com.opalynskyi.cleanmovies.core.movies.data.entities.MovieEntity
import io.reactivex.Single

class RemoteMoviesDataSourceImpl(private val api: MoviesApi, private val mapper: ServerMoviesMapper) :
    RemoteMoviesDataSource {

    override fun getMovies(startDate: String, endDate: String): Single<List<MovieEntity>> {
        return api.getOngoingMovies(startDate, endDate)
            .map { moviesResponse -> moviesResponse.movies }
            .map { movies -> movies.map(mapper::mapFromEntity).toList() }
    }
}