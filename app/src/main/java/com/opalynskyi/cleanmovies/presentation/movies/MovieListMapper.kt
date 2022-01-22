package com.opalynskyi.cleanmovies.presentation.movies

import androidx.annotation.StringRes
import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.R
import com.opalynskyi.cleanmovies.domain.entities.Movie
import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MovieItem

class MovieListMapper(
    private val dateTimeHelper: DateTimeHelper
) {
    fun mapToMovieItem(
        movie: Movie,
        @StringRes
        btnFavouriteTextRes: Int,
        btnFavouriteAction: (Boolean) -> Unit,
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
        btnFavouriteTextRes = btnFavouriteTextRes,
        btnFavouriteAction = btnFavouriteAction,
        btnShareAction = btnShareAction,
        isFavourite = movie.isFavourite
    )
}