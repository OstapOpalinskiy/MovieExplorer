package com.opalynskyi.cleanmovies.data

import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.asEither
import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.api.ServerMoviesMapper
import com.opalynskyi.cleanmovies.domain.entities.Movie
import timber.log.Timber

class RemoteMoviesDataSourceImpl(
    private val api: MoviesApi,
    private val mapper: ServerMoviesMapper
) : RemoteMoviesDataSource {
    override suspend fun getMovies(
        startDate: String,
        endDate: String
    ): Either<Exception, List<Movie>> {
        return asEither {
            Timber.d("Date range api: $startDate, $endDate")
            val response = api.getOngoingMovies(startDate, endDate)
            response.movies?.map(mapper::mapFromEntity) ?: emptyList()
        }
    }

    override suspend fun getMoviesPaged(page: Int): Either<Exception, List<Movie>> {
        return asEither {
            val response = api.getPopular(page.toString())
            response.movies?.map(mapper::mapFromEntity) ?: emptyList()
        }
    }
}