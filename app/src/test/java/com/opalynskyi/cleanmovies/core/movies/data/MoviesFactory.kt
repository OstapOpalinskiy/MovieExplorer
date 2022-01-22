package com.opalynskyi.cleanmovies.core.movies.data

import com.opalynskyi.movies_core.domain.entities.Movie

object MoviesFactory {

    fun makeMovie() = Movie(
        id = DataFactory.randomInt(),
        overview = DataFactory.randomString(),
        releaseDateTimestamp = DataFactory.randomLong(),
        imageUrl = DataFactory.randomString(),
        title = DataFactory.randomString(),
        rating = DataFactory.randomInt().toFloat(),
        isFavourite = DataFactory.randomBoolean()
    )

    fun makeMovieEntity() = Movie(
        id = DataFactory.randomInt(),
        overview = DataFactory.randomString(),
        releaseDateTimestamp = DataFactory.randomLong(),
        imageUrl = DataFactory.randomString(),
        title = DataFactory.randomString(),
        rating = DataFactory.randomInt().toFloat(),
        isFavourite = DataFactory.randomBoolean()
    )

    fun makeListOfMovies(size: Int): List<Movie> {
        val movies = mutableListOf<Movie>()
        repeat(size) {
            movies.add(makeMovie())
        }
        return movies
    }

    fun makeListOfMovieEntities(size: Int): List<Movie> {
        val movies = mutableListOf<Movie>()
        repeat(size) {
            movies.add(makeMovieEntity())
        }
        return movies
    }
}