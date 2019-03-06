package com.opalynskyi.cleanmovies.core.movies.domain

sealed class MovieEvent(val id: Int) {
    class AddToFavorite(id: Int) : MovieEvent(id)
    class RemoveFromFavorite(id: Int) : MovieEvent(id)
}