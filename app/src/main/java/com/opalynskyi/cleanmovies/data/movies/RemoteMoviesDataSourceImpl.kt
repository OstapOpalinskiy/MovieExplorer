package com.opalynskyi.cleanmovies.data.movies

import com.opalynskyi.cleanmovies.Either
import com.opalynskyi.cleanmovies.asEither
import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.api.ServerMoviesMapper
import com.opalynskyi.cleanmovies.domain.entities.Movie
import timber.log.Timber

class RemoteMoviesDataSourceImpl(
    private val api: MoviesApi,
    private val mapper: ServerMoviesMapper
) : RemoteMoviesDataSource {
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