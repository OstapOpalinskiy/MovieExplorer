package com.opalynskyi.cleanmovies.data.movies

import com.opalynskyi.cleanmovies.Either
import com.opalynskyi.cleanmovies.asEither
import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.api.ServerMoviesMapper
import com.opalynskyi.cleanmovies.domain.entities.Movie
import io.reactivex.Single
import timber.log.Timber

class RemoteMoviesDataSourceImpl(
    private val api: MoviesApi,
    private val mapper: ServerMoviesMapper
) :
    RemoteMoviesDataSource {

    override fun getMovies(startDate: String, endDate: String): Single<List<Movie>> {
        return api.getOngoingMovies(startDate, endDate)
            .map { moviesResponse -> moviesResponse.movies }
            .map { movies -> movies.map(mapper::mapFromEntity).toList() }
    }

    override suspend fun getMoviesEither(
        startDate: String,
        endDate: String
    ): Either<Exception, List<Movie>> {
        return asEither {
            Timber.d("Date range api: $startDate, $endDate")
            val response = api.getOngoingMoviesSync(startDate, endDate)
            response.movies?.map(mapper::mapFromEntity) ?: emptyList()
        }
    }
}