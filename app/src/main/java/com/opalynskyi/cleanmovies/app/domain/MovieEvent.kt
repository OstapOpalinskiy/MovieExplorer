package com.opalynskyi.cleanmovies.app.domain

sealed class MovieEvent(val id: Int) {
    class AddToFavorite(id: Int) : MovieEvent(id)
    class RemoveFromFavorite(id: Int) : MovieEvent(id)
}