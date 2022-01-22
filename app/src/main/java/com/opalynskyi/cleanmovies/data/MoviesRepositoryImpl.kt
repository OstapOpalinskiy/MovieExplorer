package com.opalynskyi.cleanmovies.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.opalynskyi.cleanmovies.data.paging.PagingDataWrapper
import com.opalynskyi.cleanmovies.data.paging.PagingSourceFactory
import com.opalynskyi.common.Either
import com.opalynskyi.movies_core.domain.MoviesRepository
import com.opalynskyi.common.asEither
import com.opalynskyi.movies_core.domain.entities.Movie
import com.opalynskyi.movies_core.domain.entities.MoviePage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesRepositoryImpl(
    private val remoteDataSource: RemoteMoviesDataSource,
    private val localDataSource: LocalMoviesDataSource,
    private val pagingSourceFactory: PagingSourceFactory,
    private val moviesMapper: DbMoviesMapper
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

    override suspend fun addToFavourites(movie: Movie): Either<Exception, Unit> {
        return asEither {
            localDataSource.addToFavourites(
                moviesMapper.mapToEntity(movie).copy(isFavourite = true)
            )
        }
    }

    override suspend fun removeFromFavourites(movie: Movie): Either<Exception, Boolean> {
        return asEither {
            val rowsAffected = localDataSource.removeFromFavourites(
                moviesMapper.mapToEntity(movie).copy(isFavourite = false)
            )
            if (rowsAffected < 1) {
                throw RuntimeException("Error removing from favourites,  movie: ${movie.id}")
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
            pagingSourceFactory.newInstance()
        }.flow.map { PagingDataWrapper(it) }
    }
}