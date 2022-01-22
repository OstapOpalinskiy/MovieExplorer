package com.opalynskyi.movies_list

import androidx.annotation.StringRes
import com.opalynskyi.movies_core.domain.entities.Movie
import com.opalynskyi.utils.DateTimeHelper

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