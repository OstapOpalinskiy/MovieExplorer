package com.opalynskyi.cleanmovies.presentation.moviesList

import com.opalynskyi.cleanmovies.presentation.adapter.MoviesListItem

data class MoviesScreenState(
    val items: List<MoviesListItem>,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
    val isEmpty: Boolean
)