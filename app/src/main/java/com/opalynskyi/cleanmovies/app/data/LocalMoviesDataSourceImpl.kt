package com.opalynskyi.cleanmovies.app.data

import com.opalynskyi.cleanmovies.app.data.database.MoviesDao
import com.opalynskyi.cleanmovies.app.domain.entities.MovieEntity

class LocalMoviesDataSourceImpl(private val dao: MoviesDao, private val mapper: DbMoviesMapper) :
    LocalMoviesDataSource {

    override fun getAll(): List<MovieEntity> {
        return dao.getAll().map { mapper.mapFromEntity(it) }
    }

    override fun saveAll(movieEntities: List<MovieEntity>) {
        val moviesToSave = movieEntities
            .map { mapper.mapToEntity(it) }
        return dao.insertAll(*moviesToSave.toTypedArray())
    }


    override fun getFavourites(): List<MovieEntity> {
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