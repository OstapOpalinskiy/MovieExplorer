package com.opalynskyi.movies

import androidx.annotation.StringRes
import com.opalynskyi.core.domain.entities.Movie
import com.opalynskyi.movieslist.R
import com.opalynskyi.utils.DateTimeHelper

class MovieListBuilder(
    private val dateTimeHelper: DateTimeHelper,
) {
    fun mapToItem(
        movie: Movie,
        isFavourite: Boolean,
        onFavouriteAction: (Boolean) -> Unit,
        onShareAction: () -> Unit,
    ): MovieItem {
        return mapToMovieItem(
            movie = movie.copy(isFavourite = isFavourite),
            btnFavouriteTextRes =
                if (movie.isFavourite) {
                    R.string.movies_list_remove_from_favourites
                } else {
                    R.string.movies_list_add_to_favourites
                },
            btnFavouriteAction = onFavouriteAction,
            btnShareAction = onShareAction,
        )
    }

    private fun mapToMovieItem(
        movie: Movie,
        @StringRes
        btnFavouriteTextRes: Int,
        btnFavouriteAction: (Boolean) -> Unit,
        btnShareAction: () -> Unit,
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
        isFavourite = movie.isFavourite,
    )
}
