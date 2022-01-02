package com.opalynskyi.cleanmovies.data.api

import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.data.api.ApiConstants.BASE_IMG_URL
import com.opalynskyi.cleanmovies.domain.entities.Movie

class ServerMoviesMapper(private val dateTimeHelper: DateTimeHelper) {

    fun mapFromEntity(entity: ServerMovie) = Movie(
        entity.id,
        entity.overview,
        dateTimeHelper.getTimestampFrom(entity.releaseDate),
        BASE_IMG_URL + entity.posterPath,
        entity.title,
        entity.voteAverage,
        false
    )
}