package com.opalynskyi.cleanmovies.data.api

import com.opalynskyi.cleanmovies.data.api.ApiConstants.BASE_IMG_URL
import com.opalynskyi.movies_core.domain.entities.Movie

class ServerMoviesMapper(private val dateTimeHelper: com.opalynskyi.utils.DateTimeHelper) {

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