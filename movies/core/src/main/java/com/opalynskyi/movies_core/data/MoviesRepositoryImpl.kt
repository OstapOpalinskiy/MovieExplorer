package com.opalynskyi.movies_core.data

import com.opalynskyi.common.Either
import com.opalynskyi.common.asEither
import com.opalynskyi.movies_core.domain.MoviesRepository
import com.opalynskyi.movies_core.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    private val localDataSource: LocalMoviesDataSource,
    private val moviesMapper: DbMoviesMapper
) : MoviesRepository {

    override suspend fun observeFavouriteMovies(): Flow<List<Movie>> {
        return localDataSource.getAllFlow()
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
}