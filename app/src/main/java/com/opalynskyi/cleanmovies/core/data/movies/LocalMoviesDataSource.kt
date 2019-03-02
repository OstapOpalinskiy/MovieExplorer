package com.opalynskyi.cleanmovies.core.data.movies

import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity
import io.reactivex.Completable
import io.reactivex.Single

interface LocalMoviesDataSource {
    fun getAll(): List<MovieEntity>
    fun saveAll(movieEntities: List<MovieEntity>)
    fun getFavourites(): List<MovieEntity>
    fun addToFavourites(movieEntity: MovieEntity)
    fun removeFromFavourites(movieEntity: MovieEntity): Int
}