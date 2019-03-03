package com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource

import com.opalynskyi.cleanmovies.app.database.MoviesDao
import com.opalynskyi.cleanmovies.core.data.movies.LocalMoviesDataSource
import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity

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

    override fun addToFavourites(movieEntity: MovieEntity) {
        movieEntity.isFavourite = true
        return dao.insert(mapper.mapToEntity(movieEntity))
    }

    override fun removeFromFavourites(movieEntity: MovieEntity): Int {
        return dao.delete(mapper.mapToEntity(movieEntity))
    }
}