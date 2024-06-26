package com.opalynskyi.core.data

import com.opalynskyi.core.domain.entities.Movie
import com.opalynskyi.db.MovieDbEntity
import kotlinx.coroutines.flow.Flow

interface LocalMoviesDataSource {
    fun getAll(): List<Movie>

    fun getAllFlow(): Flow<List<Movie>>

    fun saveAll(movies: List<Movie>)

    fun getFavourites(): List<Movie>

    fun addToFavourites(movie: MovieDbEntity)

    fun removeFromFavourites(movie: MovieDbEntity): Int
}
