package com.opalynskyi.cleanmovies.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.opalynskyi.cleanmovies.data.paging.MoviesPagingSource
import com.opalynskyi.cleanmovies.data.paging.PagingDataWrapper
import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import com.opalynskyi.cleanmovies.domain.asEither
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.domain.entities.MoviePage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(
    private val remoteDataSource: RemoteMoviesDataSource,
    private val localDataSource: LocalMoviesDataSource,
    private val pagingSource: MoviesPagingSource
) : MoviesRepository {

    override suspend fun observeMovies(): Flow<List<Movie>> {
        return localDataSource.getAllFlow()
    }

    override suspend fun getMovies(
        startDate: String,
        endDate: String
    ): Either<Exception, List<Movie>> {
        return when (val result = remoteDataSource.getMovies(startDate, endDate)) {
            is Either.Value -> {
                val favourites = localDataSource.getFavourites()
                val movies = result.value.map { movie ->
                    val localMovie = favourites.firstOrNull { movie.id == it.id }
                    movie.isFavourite = localMovie?.isFavourite ?: false
                    movie
                }
                localDataSource.saveAll(movies)
                Either.Value(movies)
            }
            is Either.Error -> result
        }
    }

    override suspend fun getFavourites(): Either<Exception, List<Movie>> {
        return asEither { localDataSource.getFavourites() }
    }

    override suspend fun addToFavourites(id: Int): Either<Exception, Boolean> {
        return asEither {
            val rowsAffected = localDataSource.addToFavourites(id)
            if (rowsAffected < 1) {
                throw RuntimeException("Error adding to favourites movie: $id")
            } else {
                true
            }
        }
    }

    override suspend fun removeFromFavourites(id: Int): Either<Exception, Boolean> {
        return asEither {
            val rowsAffected = localDataSource.removeFromFavourites(id)
            if (rowsAffected < 1) {
                throw RuntimeException("Error removing from favourites,  movie: $id")
            } else {
                true
            }
        }
    }

    override fun getMoviesPage(
        pageSize: Int,
        prefetchDistance: Int,
        maxCachedPagesSize: Int
    ): Flow<MoviePage> {
        return Pager(
            PagingConfig(
                pageSize = pageSize,
                prefetchDistance = prefetchDistance,
                maxSize = maxCachedPagesSize
            )
        ) {
            pagingSource
        }.flow.map { PagingDataWrapper(it) }
    }
}