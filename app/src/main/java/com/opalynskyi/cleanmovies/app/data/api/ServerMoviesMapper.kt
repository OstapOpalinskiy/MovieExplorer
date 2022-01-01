package com.opalynskyi.cleanmovies.app.data.api

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.data.api.ApiConstants.BASE_IMG_URL
import com.opalynskyi.cleanmovies.app.domain.entities.MovieEntity

class ServerMoviesMapper(private val dateTimeHelper: DateTimeHelper) {

    fun mapFromEntity(entity: ServerMovie) = MovieEntity(
        entity.id,
        entity.overview,
        dateTimeHelper.getTimestampFrom(entity.releaseDate),
        BASE_IMG_URL + entity.posterPath,
        entity.title,
        entity.voteAverage,
        false
    )
}