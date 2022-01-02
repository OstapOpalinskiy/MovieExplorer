package com.opalynskyi.cleanmovies.domain

sealed class MovieEvent(val id: Int) {
    class AddToFavorite(id: Int) : MovieEvent(id)
    class RemoveFromFavorite(id: Int) : MovieEvent(id)
}