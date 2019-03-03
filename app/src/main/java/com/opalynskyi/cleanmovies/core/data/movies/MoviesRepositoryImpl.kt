package com.opalynskyi.cleanmovies.core.data.movies

import com.opalynskyi.cleanmovies.core.data.EntityMapper
import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesRepository
import com.opalynskyi.cleanmovies.core.domain.movies.entities.Movie
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber

class MoviesRepositoryImpl(
    private val remoteDataSource: RemoteMoviesDataSource,
    private val localDataSource: LocalMoviesDataSource,
    private val mapper: EntityMapper<MovieEntity, Movie>
) : MoviesRepository {

    override fun getMovies(startDate: String, endDate: String): Single<List<Movie>> {
        return getRemoteMovies(startDate, endDate)
            .onErrorReturn {
                Timber.d("getRemoteMovies() failed, with error: ${it.message}")
                getLocalMovies()
            }
    }

    private fun getRemoteMovies(startDate: String, endDate: String): Single<List<Movie>> {
        Timber.d("Get remote movies: startDate: $startDate, endDate: $endDate")
        return remoteDataSource.getMovies(startDate, endDate)
            .flattenAsObservable { movieEntities -> movieEntities }
            .map(mapper::mapFromEntity)
            .toList()
            .doOnSuccess {
                Timber.d("Save remote movies in local storage")
                saveMovies(it)
            }
    }

    private fun saveMovies(movies: List<Movie>) {
        val newMovies = movies.map { mapper.mapToEntity(it) }
        val favourites = localDataSource.getFavourites()
        newMovies.forEach { setFavourite(it, favourites) }
        localDataSource.saveAll(newMovies)
    }

    private fun setFavourite(movie: MovieEntity, localMovies: List<MovieEntity>) {
        val localMovie = localMovies.firstOrNull { movie.id == it.id }
        localMovie?.let { movie.isFavourite = it.isFavourite }
    }


    override fun getFavourites(): Single<List<Movie>> {
        Timber.d("Get favourite movies")
        return Single.fromCallable {
            localDataSource.getFavourites()
                .map(mapper::mapFromEntity)
        }
    }

    override fun addToFavourites(id: Int): Completable {
        Timber.d("Add to favourites: $id")
        return Completable.fromCallable {
            val rowsAffected = localDataSource.addToFavourites(id)
            if (rowsAffected < 1) {
                throw RuntimeException("Error updating movie: $id")
            }
        }
    }

    override fun removeFromFavourites(id: Int): Completable {
        Timber.d("removeFrom favourites: $id")
        return Completable.fromCallable {
            val rowsAffected = localDataSource.removeFromFavourites(id)
            if (rowsAffected < 1) {
                throw RuntimeException("Error deleting movie: $id")
            }
        }
    }

    // TODO:  getLocalMovies() should also receive startDate, endDate parameters
    private fun getLocalMovies(): List<Movie> {
        Timber.d("Get local movies")
        return localDataSource.getAll()
            .map(mapper::mapFromEntity)
    }
}