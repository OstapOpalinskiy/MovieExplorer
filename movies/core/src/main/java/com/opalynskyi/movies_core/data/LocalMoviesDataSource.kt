package com.opalynskyi.movies_core.data

import com.opalynskyi.db.MovieDbEntity
import com.opalynskyi.movies_core.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface LocalMoviesDataSource {
    fun getAll(): List<Movie>
    fun getAllFlow(): Flow<List<Movie>>
    fun saveAll(movies: List<Movie>)
    fun getFavourites(): List<Movie>
    fun addToFavourites(movie: MovieDbEntity)
    fun removeFromFavourites(movie: MovieDbEntity): Int
}