package com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource

import com.opalynskyi.cleanmovies.app.database.MoviesDao
import com.opalynskyi.cleanmovies.core.movies.data.LocalMoviesDataSource
import com.opalynskyi.cleanmovies.core.movies.data.entities.MovieEntity

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