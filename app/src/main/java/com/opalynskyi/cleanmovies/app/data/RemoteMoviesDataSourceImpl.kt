package com.opalynskyi.cleanmovies.app.data

import com.opalynskyi.cleanmovies.app.data.api.MoviesApi
import com.opalynskyi.cleanmovies.app.domain.entities.MovieEntity
import com.opalynskyi.cleanmovies.app.data.api.ServerMoviesMapper
import io.reactivex.Single

class RemoteMoviesDataSourceImpl(private val api: MoviesApi, private val mapper: ServerMoviesMapper) :
    RemoteMoviesDataSource {

    override fun getMovies(startDate: String, endDate: String): Single<List<MovieEntity>> {
        return api.getOngoingMovies(startDate, endDate)
            .map { moviesResponse -> moviesResponse.movies }
            .map { movies -> movies.map(mapper::mapFromEntity).toList() }
    }
}