package com.opalynskyi.cleanmovies.data

import com.opalynskyi.cleanmovies.domain.Either
import com.opalynskyi.cleanmovies.domain.asEither
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import com.opalynskyi.cleanmovies.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    private val remoteDataSource: RemoteMoviesDataSource,
    private val localDataSource: LocalMoviesDataSource
) : MoviesRepository {

    override suspend fun observeMovies(): Flow<List<Movie>> {
        return localDataSource.getAllFlow()
    }

    override suspend fun getMoviesEither(
        startDate: String,
        endDate: String
    ): Either<Exception, List<Movie>> {
        return when (val result = remoteDataSource.getMoviesEither(startDate, endDate)) {
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

    override suspend fun getFavouritesEither(): Either<Exception, List<Movie>> {
        return asEither { localDataSource.getFavourites() }
    }

    override suspend fun addToFavouritesEither(id: Int): Either<Exception, Boolean> {
        return asEither {
            val rowsAffected = localDataSource.addToFavourites(id)
            if (rowsAffected < 1) {
                throw RuntimeException("Error adding to favourites movie: $id")
            } else {
                true
            }
        }
    }

    override suspend fun removeFromFavouritesEither(id: Int): Either<Exception, Boolean> {
        return asEither {
            val rowsAffected = localDataSource.removeFromFavourites(id)
            if (rowsAffected < 1) {
                throw RuntimeException("Error removing from favourites,  movie: $id")
            } else {
                true
            }
        }
    }
}