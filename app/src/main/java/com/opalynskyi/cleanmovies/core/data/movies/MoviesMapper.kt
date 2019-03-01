package com.opalynskyi.cleanmovies.core.data.movies

import com.opalynskyi.cleanmovies.core.data.EntityMapper
import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity
import com.opalynskyi.cleanmovies.core.domain.movies.entities.MovieModel

class MoviesMapper : EntityMapper<MovieEntity, MovieModel> {
    override fun mapFromEntity(entity: MovieEntity) = MovieModel(
        entity.id,
        entity.backdropPath,
        entity.genreIds,
        entity.originalLanguage,
        entity.originalTitle,
        entity.overview,
        entity.releaseDate,
        entity.posterPath,
        entity.popularity,
        entity.title,
        entity.isVideo,
        entity.voteAverage,
        entity.voteCount
    )

    override fun mapToEntity(domain: MovieModel) = MovieEntity(
        domain.id,
        domain.backdropPath,
        domain.genreIds,
        domain.originalLanguage,
        domain.originalTitle,
        domain.overview,
        domain.releaseDate,
        domain.posterPath,
        domain.popularity,
        domain.title,
        domain.isVideo,
        domain.voteAverage,
        domain.voteCount
    )
}