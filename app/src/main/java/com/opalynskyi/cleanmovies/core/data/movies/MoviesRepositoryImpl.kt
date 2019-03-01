package com.opalynskyi.cleanmovies.core.data.movies

import com.opalynskyi.cleanmovies.core.data.EntityMapper
import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesRepository
import com.opalynskyi.cleanmovies.core.domain.movies.entities.MovieModel
import io.reactivex.Single

class MoviesRepositoryImpl(
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource,
    private val mapper: EntityMapper<MovieEntity, MovieModel>
) : MoviesRepository {
    override fun getMovies(startDate: String, endDate: String): Single<List<MovieModel>> {
        return getRemoteMovies(startDate, endDate)

    }

    private fun getRemoteMovies(startDate: String, endDate: String): Single<List<MovieModel>> {
        return remoteMoviesDataSource.getMovies(startDate, endDate)
            .flattenAsObservable { movieEntities -> movieEntities }
            .map(mapper::mapFromEntity)
            .toList()
    }
}