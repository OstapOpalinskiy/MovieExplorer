package com.opalynskyi.network.api

import com.opalynskyi.core.domain.entities.Movie
import com.opalynskyi.network.api.ApiConstants.BASE_IMG_URL
import com.opalynskyi.utils.DateTimeHelper

class ServerMoviesMapper(private val dateTimeHelper: DateTimeHelper) {
    fun mapFromEntity(entity: ServerMovie) =
        Movie(
            entity.id,
            entity.overview,
            dateTimeHelper.getTimestampFrom(entity.releaseDate),
            BASE_IMG_URL + entity.posterPath,
            entity.title,
            entity.voteAverage,
            false,
        )
}
