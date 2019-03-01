package com.opalynskyi.cleanmovies.app.movies.datasource

import com.opalynskyi.cleanmovies.app.api.MovieResponce
import com.opalynskyi.cleanmovies.core.data.EntityMapper
import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity

class ResponseMoviesMapper : EntityMapper<MovieResponce, MovieEntity> {

    override fun mapFromEntity(entity: MovieResponce) = MovieEntity(
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

    override fun mapToEntity(domain: MovieEntity) = MovieResponce(
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