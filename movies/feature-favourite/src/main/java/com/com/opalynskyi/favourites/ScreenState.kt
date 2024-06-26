package com.com.opalynskyi.favourites

import com.opalynskyi.movies.MoviesListItem

data class ScreenState(
    val items: List<MoviesListItem> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isEmpty: Boolean = false,
) {
    // Use default equals() implementation to make StateFlow send same state twice
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
