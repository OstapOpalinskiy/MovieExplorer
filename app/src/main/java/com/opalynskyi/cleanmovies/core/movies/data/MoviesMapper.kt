package com.opalynskyi.cleanmovies.core.movies.data

import com.opalynskyi.cleanmovies.core.movies.data.entities.MovieEntity
import com.opalynskyi.cleanmovies.core.movies.domain.entities.Movie

class MoviesMapper : EntityMapper<MovieEntity, Movie> {
    override fun mapFromEntity(entity: MovieEntity) = Movie(
        entity.id,
        entity.overview,
        entity.releaseDate,
        entity.posterPath,
        entity.title,
        entity.voteAverage,
        entity.isFavourite
    )

    override fun mapToEntity(domain: Movie) = MovieEntity(
        domain.id,
        domain.overview,
        domain.releaseDate,
        domain.posterPath,
        domain.title,
        domain.voteAverage,
        domain.isFavourite
    )
}