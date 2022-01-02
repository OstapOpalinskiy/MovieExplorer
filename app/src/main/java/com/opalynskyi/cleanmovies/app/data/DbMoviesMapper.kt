package com.opalynskyi.cleanmovies.app.data

import com.opalynskyi.cleanmovies.app.data.database.MovieDbEntity
import com.opalynskyi.cleanmovies.app.domain.entities.Movie

class DbMoviesMapper : EntityMapper<MovieDbEntity, Movie> {
    override fun mapFromEntity(entity: MovieDbEntity) = Movie(
        entity.id,
        entity.overview,
        entity.releaseDate,
        entity.posterPath,
        entity.title,
        entity.voteAverage,
        entity.isFavourite
    )

    override fun mapToEntity(domain: Movie) = MovieDbEntity(
        domain.id,
        domain.overview,
        domain.releaseDateTimestamp,
        domain.posterUrl,
        domain.title,
        domain.rating,
        domain.isFavourite
    )
}
