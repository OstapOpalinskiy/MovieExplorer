package com.opalynskyi.cleanmovies.presentation

import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.presentation.adapter.MovieItem

class MovieListMapper(
    private val dateTimeHelper: DateTimeHelper,
    ) {
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