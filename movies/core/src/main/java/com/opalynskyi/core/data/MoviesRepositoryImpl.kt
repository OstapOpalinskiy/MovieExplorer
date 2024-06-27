package com.opalynskyi.core.data

import com.opalynskyi.common.Either
import com.opalynskyi.common.asEither
import com.opalynskyi.core.domain.MoviesRepository
import com.opalynskyi.core.domain.entities.Movie
import com.opalynskyi.core.domain.exceptions.RemoveFromFavouritesFailedException
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl(
    private val localDataSource: LocalMoviesDataSource,
    private val moviesMapper: DbMoviesMapper,
) : MoviesRepository {
    override suspend fun observeFavouriteMovies(): Flow<List<Movie>> = localDataSource.getAllFlow()

    override suspend fun getFavourites(): Either<Exception, List<Movie>> = asEither { localDataSource.getFavourites() }

    override suspend fun addToFavourites(movie: Movie): Either<Exception, Unit> =
        asEither {
            localDataSource.addToFavourites(
                moviesMapper.mapToEntity(movie).copy(isFavourite = true),
            )
        }

    override suspend fun removeFromFavourites(movie: Movie): Either<Exception, Boolean> =
        asEither {
            val rowsAffected =
                localDataSource.removeFromFavourites(
                    moviesMapper.mapToEntity(movie).copy(isFavourite = false),
                )
            if (rowsAffected < 1) {
                throw RemoveFromFavouritesFailedException("Error removing from favourites,  movie: ${movie.id}")
            } else {
                true
            }
        }
}
