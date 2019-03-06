package com.opalynskyi.cleanmovies.core.movies.data

import com.opalynskyi.cleanmovies.core.movies.data.entities.MovieEntity

interface LocalMoviesDataSource {
    fun getAll(): List<MovieEntity>
    fun saveAll(movieEntities: List<MovieEntity>)
    fun getFavourites(): List<MovieEntity>
    fun addToFavourites(id: Int): Int
    fun removeFromFavourites(id: Int): Int
}