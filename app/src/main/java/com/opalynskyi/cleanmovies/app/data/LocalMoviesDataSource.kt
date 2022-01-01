package com.opalynskyi.cleanmovies.app.data

import com.opalynskyi.cleanmovies.app.domain.entities.MovieEntity

interface LocalMoviesDataSource {
    fun getAll(): List<MovieEntity>
    fun saveAll(movieEntities: List<MovieEntity>)
    fun getFavourites(): List<MovieEntity>
    fun addToFavourites(id: Int): Int
    fun removeFromFavourites(id: Int): Int
}