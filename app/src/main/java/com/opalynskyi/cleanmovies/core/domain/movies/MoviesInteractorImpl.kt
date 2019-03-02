package com.opalynskyi.cleanmovies.core.domain.movies

import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.opalynskyi.cleanmovies.core.domain.movies.entities.Movie
import io.reactivex.Completable
import io.reactivex.Single

class MoviesInteractorImpl(
    private val repository: MoviesRepository,
    private val scheduler: SchedulerProvider
) : MoviesInteractor {

    override fun getMovies(startDate: String, endDate: String): Single<List<Movie>> {
        return repository.getMovies(startDate, endDate)
            .subscribeOn(scheduler.backgroundThread())
    }

    override fun addToFavourites(movie: Movie): Completable {
        return repository.addToFavourites(movie)
            .subscribeOn(scheduler.backgroundThread())
    }

    override fun removeFromFavourites(movie: Movie): Completable {
        return repository.removeFromFavourites(movie)
            .subscribeOn(scheduler.backgroundThread())
    }

    override fun getFavourites(): Single<List<Movie>> {
        return repository.getFavourites()
            .subscribeOn(scheduler.backgroundThread())
    }
}