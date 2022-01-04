package com.opalynskyi.cleanmovies.presentation

import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MovieItem

class MovieListMapper(
    private val dateTimeHelper: DateTimeHelper
) {
    fun mapToMovieItem(
        movie: Movie,
        btnFavouriteAction: () -> Unit,
        btnShareAction: () -> Unit
    ) = MovieItem(
        id = movie.id,
        overview = movie.overview ?: "",
        imageUrl = movie.imageUrl,
        title = movie.title ?: "",
        rating = movie.rating,
        releaseDate = movie.releaseDateTimestamp,
        year = dateTimeHelper.getYear(movie.releaseDateTimestamp),
        month = dateTimeHelper.getMonth(movie.releaseDateTimestamp),
        btnFavouriteTextRes = if (movie.isFavourite) {
            R.string.remove_from_favourites
        } else {
            R.string.add_to_favourites
        },
        btnFavouriteAction = btnFavouriteAction,
        btnShareAction = btnShareAction,
        isFavourite = movie.isFavourite
    )
}