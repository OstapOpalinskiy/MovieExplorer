package com.opalynskyi.cleanmovies.data

import com.opalynskyi.cleanmovies.data.database.MovieDbEntity
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

    override fun addToFavourites(movie: MovieDbEntity) {
        dao.insert(movie)
    }

    override fun removeFromFavourites(movie: MovieDbEntity): Int {
        return dao.delete(movie)
    }
}