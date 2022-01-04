package com.opalynskyi.cleanmovies.presentation.movies

import com.opalynskyi.cleanmovies.presentation.movies.movies_adapter.MoviesListItem

data class ScreenState(
    val items: List<MoviesListItem> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isEmpty: Boolean = false
) {
    // Use default equals() implementation to make StateFlow send same state twice
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}