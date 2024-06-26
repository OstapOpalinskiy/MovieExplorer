package com.opalynskyi.movies

import androidx.annotation.StringRes

data class MovieItem(
    val id: Int,
    val overview: String,
    val imageUrl: String,
    val title: String,
    val rating: Float,
    val releaseDate: Long,
    val month: Int,
    val year: Int,
    @StringRes
    val btnFavouriteTextRes: Int,
    val btnFavouriteAction: (Boolean) -> Unit,
    val btnShareAction: () -> Unit,
    var isFavourite: Boolean,
) : MoviesListItem
