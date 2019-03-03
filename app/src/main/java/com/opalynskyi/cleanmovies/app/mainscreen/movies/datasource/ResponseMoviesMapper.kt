package com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.api.ServerMovie
import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity

class ResponseMoviesMapper(private val dateTimeHelper: DateTimeHelper) {

    fun mapFromEntity(entity: ServerMovie) = MovieEntity(
        entity.id,
        entity.overview,
        dateTimeHelper.getTimestampFrom(entity.releaseDate),
        entity.posterPath,
        entity.title,
        entity.voteAverage,
        false
    )
}