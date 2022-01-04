package com.opalynskyi.cleanmovies.data

import com.opalynskyi.cleanmovies.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface LocalMoviesDataSource {
    fun getAll(): List<Movie>
    fun getAllFlow(): Flow<List<Movie>>
    fun saveAll(movies: List<Movie>)
    fun getFavourites(): List<Movie>
    fun addToFavourites(id: Int): Int
    fun removeFromFavourites(id: Int): Int
}