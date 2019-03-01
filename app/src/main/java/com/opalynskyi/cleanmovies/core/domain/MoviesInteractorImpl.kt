package com.opalynskyi.cleanmovies.core.domain

import com.opalynskyi.cleanmovies.core.domain.movies.MoviesInteractor
import com.opalynskyi.cleanmovies.core.domain.movies.MoviesRepository
import com.opalynskyi.cleanmovies.core.domain.movies.entities.MovieModel
import io.reactivex.Single

class MoviesInteractorImpl(private val moviesRepository: MoviesRepository) : MoviesInteractor {
    override fun getMovies(startDate: String, endDate: String): Single<List<MovieModel>> {
        return moviesRepository.getMovies(startDate, endDate)
    }
}