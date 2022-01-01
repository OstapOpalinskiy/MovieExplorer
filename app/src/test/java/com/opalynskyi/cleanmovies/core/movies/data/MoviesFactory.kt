package com.opalynskyi.cleanmovies.core.movies.data

import com.opalynskyi.cleanmovies.app.domain.entities.MovieEntity
import com.opalynskyi.cleanmovies.app.domain.entities.Movie

object MoviesFactory {

    fun makeMovie() = Movie(
        id = DataFactory.randomInt(),
        overview = DataFactory.randomString(),
        releaseDate = DataFactory.randomLong(),
        posterPath = DataFactory.randomString(),
        title = DataFactory.randomString(),
        voteAverage = DataFactory.randomInt().toFloat(),
        isFavourite = DataFactory.randomBoolean()
    )

    fun makeMovieEntity() = MovieEntity(
        id = DataFactory.randomInt(),
        overview = DataFactory.randomString(),
        releaseDate = DataFactory.randomLong(),
        posterPath = DataFactory.randomString(),
        title = DataFactory.randomString(),
        voteAverage = DataFactory.randomInt().toFloat(),
        isFavourite = DataFactory.randomBoolean()
    )

    fun makeListOfMovies(size: Int): List<Movie> {
        val movies = mutableListOf<Movie>()
        repeat(size) {
            movies.add(makeMovie())
        }
        return movies
    }

    fun makeListOfMovieEntities(size: Int): List<MovieEntity> {
        val movies = mutableListOf<MovieEntity>()
        repeat(size) {
            movies.add(makeMovieEntity())
        }
        return movies
    }
}