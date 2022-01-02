package com.opalynskyi.cleanmovies.app.data

import com.opalynskyi.cleanmovies.app.domain.entities.Movie

interface LocalMoviesDataSource {
    fun getAll(): List<Movie>
    fun saveAll(movies: List<Movie>)
    fun getFavourites(): List<Movie>
    fun addToFavourites(id: Int): Int
    fun removeFromFavourites(id: Int): Int
}