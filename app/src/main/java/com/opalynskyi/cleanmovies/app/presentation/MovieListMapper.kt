package com.opalynskyi.cleanmovies.app.presentation

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.presentation.adapter.MovieItem
import com.opalynskyi.cleanmovies.app.domain.entities.Movie

class MovieListMapper(private val dateTimeHelper: DateTimeHelper) {
    fun mapToMovieItem(movie: Movie) = MovieItem(
        movie.id,
        movie.overview ?: "",
        movie.releaseDateTimestamp,
        movie.posterUrl,
        movie.title ?: "",
        movie.rating,
        movie.isFavourite,
        dateTimeHelper.getYear(movie.releaseDateTimestamp),
        dateTimeHelper.getMonth(movie.releaseDateTimestamp)
    )
}