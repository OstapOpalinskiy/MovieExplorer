package com.opalynskyi.cleanmovies.data

import com.opalynskyi.cleanmovies.data.database.MoviesDao
import com.opalynskyi.cleanmovies.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMoviesDataSourceImpl(private val dao: MoviesDao, private val mapper: DbMoviesMapper) :
    LocalMoviesDataSource {

    override fun getAll(): List<Movie> {
        return dao.getAll().map { mapper.mapFromEntity(it) }
    }

    override fun getAllFlow(): Flow<List<Movie>> {
        return dao.getAllFlow()
            .map { list -> list.map { dbMovie -> mapper.mapFromEntity(dbMovie) } }
    }

    override fun saveAll(movies: List<Movie>) {
        val moviesToSave = movies.map { mapper.mapToEntity(it) }
        return dao.insertAll(*moviesToSave.toTypedArray())
    }


    override fun getFavourites(): List<Movie> {
        return dao.getFavourite().map { mapper.mapFromEntity(it) }
    }

    override fun addToFavourites(id: Int): Int {
        val movie = dao.getById(id)
        movie.isFavourite = true
        return dao.update(movie)
    }

    override fun removeFromFavourites(id: Int): Int {
        val movie = dao.getById(id)
        movie.isFavourite = false
        return dao.update(movie)
    }
}