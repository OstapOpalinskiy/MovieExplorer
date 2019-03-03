package com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource

import com.opalynskyi.cleanmovies.app.database.MovieDbEntity
import com.opalynskyi.cleanmovies.core.data.EntityMapper
import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity

class DbMoviesMapper : EntityMapper<MovieDbEntity, MovieEntity> {
    override fun mapFromEntity(entity: MovieDbEntity) = MovieEntity(
        entity.id,
        entity.overview,
        entity.releaseDate,
        entity.posterPath,
        entity.title,
        entity.voteAverage,
        entity.isFavourite
    )

    override fun mapToEntity(domain: MovieEntity) = MovieDbEntity(
        domain.id,
        domain.overview,
        domain.releaseDate,
        domain.posterPath,
        domain.title,
        domain.voteAverage,
        domain.isFavourite
    )
}
