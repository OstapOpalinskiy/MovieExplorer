package com.opalynskyi.cleanmovies.data.movies

import com.opalynskyi.cleanmovies.Either
import com.opalynskyi.cleanmovies.asEither
import com.opalynskyi.cleanmovies.domain.MovieEvent
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import com.opalynskyi.cleanmovies.domain.entities.Movie
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import timber.log.Timber

class MoviesRepositoryImpl(
    private val remoteDataSource: RemoteMoviesDataSource,
    private val localDataSource: LocalMoviesDataSource
) : MoviesRepository {

    private val repositoryEventsSubject: Subject<MovieEvent> =
        makeThreadSafe(PublishSubject.create())

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
            .map { movie ->
                // set isFavourite flag to movie from remote data source
                val favourites = localDataSource.getFavourites()
                val localMovie = favourites.firstOrNull { movie.id == it.id }
                movie.isFavourite = localMovie?.isFavourite ?: false
                movie
            }
            .toList()
            .doOnSuccess {
                Timber.d("Save remote movies in local storage")
                localDataSource.saveAll(it)
            }
    }


    override fun getFavourites(): Single<List<Movie>> {
        Timber.d("Get favourite movies")
        return Single.fromCallable {
            localDataSource.getFavourites()
        }
    }

    override fun addToFavourites(id: Int): Completable {
        Timber.d("Add to favourites: $id, repository:$this")
        return Completable.fromCallable {
            val rowsAffected = localDataSource.addToFavourites(id)
            if (rowsAffected < 1) {
                throw RuntimeException("Error updating movie: $id")
            }
            repositoryEventsSubject.onNext(MovieEvent.AddToFavorite(id))
        }
    }

    override fun removeFromFavourites(id: Int): Completable {
        Timber.d("removeFrom favourites: $id, repository:$this")
        return Completable.fromCallable {
            val rowsAffected = localDataSource.removeFromFavourites(id)
            if (rowsAffected < 1) {
                throw RuntimeException("Error deleting movie: $id")
            }
            repositoryEventsSubject.onNext(MovieEvent.RemoveFromFavorite(id))
        }
    }

    // TODO:  getLocalMovies() should also receive startDate, endDate parameters
    private fun getLocalMovies(): List<Movie> {
        Timber.d("Get local movies")
        return localDataSource.getAll()
    }

    override fun bindEvents(): Observable<MovieEvent> {
        return repositoryEventsSubject.hide()
    }

    override suspend fun getMoviesEither(
        startDate: String,
        endDate: String
    ): Either<Exception, List<Movie>> {
        return remoteDataSource.getMoviesEither(startDate, endDate)
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

    private fun <T> makeThreadSafe(s: Subject<T>): Subject<T> {
        return s.toSerialized()
    }
}