package com.opalynskyi.core.data

import com.opalynskyi.core.domain.entities.Movie
import com.opalynskyi.db.MoviesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMoviesDataSourceImpl(
    private val dao: MoviesDao,
    private val mapper: DbMoviesMapper,
) : LocalMoviesDataSource {
    override fun getAll(): List<Movie> = dao.getAll().map { mapper.mapFromEntity(it) }

    override fun getAllFlow(): Flow<List<Movie>> =
        dao
            .getAllFlow()
            .map { list -> list.map { dbMovie -> mapper.mapFromEntity(dbMovie) } }

    @Suppress("SpreadOperator")
    override fun saveAll(movies: List<Movie>) {
        val moviesToSave = movies.map { mapper.mapToEntity(it) }
        return dao.insertAll(*moviesToSave.toTypedArray())
    }

    override fun getFavourites(): List<Movie> = dao.getFavourite().map { mapper.mapFromEntity(it) }

    override fun addToFavourites(movie: com.opalynskyi.db.MovieDbEntity) {
        dao.insert(movie)
    }

    override fun removeFromFavourites(movie: com.opalynskyi.db.MovieDbEntity): Int = dao.delete(movie)
}
