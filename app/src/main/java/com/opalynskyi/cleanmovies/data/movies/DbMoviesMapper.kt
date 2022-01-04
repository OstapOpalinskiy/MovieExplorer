package com.opalynskyi.cleanmovies.data.movies

import com.opalynskyi.cleanmovies.data.EntityMapper
import com.opalynskyi.cleanmovies.data.database.MovieDbEntity
import com.opalynskyi.cleanmovies.domain.entities.Movie

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
        domain.imageUrl,
        domain.title,
        domain.rating,
        domain.isFavourite
    )
}
