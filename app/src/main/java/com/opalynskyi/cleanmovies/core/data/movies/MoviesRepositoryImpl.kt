package com.opalynskyi.cleanmovies.core.data.movies

import com.opalynskyi.cleanmovies.core.data.EntityMapper
import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity
import com.opalynskyi.cleanmovies.core.domain.movies.MovieEvent
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesRepository
import com.opalynskyi.cleanmovies.core.domain.movies.entities.Movie
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import timber.log.Timber

class MoviesRepositoryImpl(
    private val remoteDataSource: RemoteMoviesDataSource,
    private val localDataSource: LocalMoviesDataSource,
    private val mapper: EntityMapper<MovieEntity, Movie>
) : MoviesRepository {

    private val repositoryEventsSubject: Subject<MovieEvent> = makeThreadSafe(PublishSubject.create())

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
                localDataSource.saveAll(it.map(mapper::mapToEntity))
            }
    }


    override fun getFavourites(): Single<List<Movie>> {
        Timber.d("Get favourite movies")
        return Single.fromCallable {
            localDataSource.getFavourites()
                .map(mapper::mapFromEntity)
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
            .map(mapper::mapFromEntity)
    }

    override fun bindEvents(): Observable<MovieEvent> {
        return repositoryEventsSubject.hide()
    }

    private fun <T> makeThreadSafe(s: Subject<T>): Subject<T> {
        return s.toSerialized()
    }
}