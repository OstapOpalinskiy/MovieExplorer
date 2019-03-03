package com.opalynskyi.cleanmovies.core.data.movies

import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity

interface LocalMoviesDataSource {
    fun getAll(): List<MovieEntity>
    fun saveAll(movieEntities: List<MovieEntity>)
    fun getFavourites(): List<MovieEntity>
    fun addToFavourites(id: Int): Int
    fun removeFromFavourites(id: Int): Int
}