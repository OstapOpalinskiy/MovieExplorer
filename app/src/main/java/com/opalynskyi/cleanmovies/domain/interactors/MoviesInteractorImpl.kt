package com.opalynskyi.cleanmovies.domain.interactors

import com.opalynskyi.cleanmovies.SchedulerProvider
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.domain.MovieEvent
import com.opalynskyi.cleanmovies.domain.MoviesRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class MoviesInteractorImpl(
    private val repository: MoviesRepository,
    private val scheduler: SchedulerProvider
) : MoviesInteractor {

    override fun getMovies(startDate: String, endDate: String): Single<List<Movie>> {
        return repository.getMovies(startDate, endDate)
            .subscribeOn(scheduler.backgroundThread())
    }

    override fun addToFavourites(id: Int): Completable {
        return repository.addToFavourites(id)
            .subscribeOn(scheduler.backgroundThread())
    }

    override fun removeFromFavourites(id: Int): Completable {
        return repository.removeFromFavourites(id)
            .subscribeOn(scheduler.backgroundThread())
    }

    override fun getFavourites(): Single<List<Movie>> {
        return repository.getFavourites()
            .subscribeOn(scheduler.backgroundThread())
    }

    override fun bindEvents(): Observable<MovieEvent> {
        return repository.bindEvents()
    }
}