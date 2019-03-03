package com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource

import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.api.ApiConstants.BASE_IMG_URL
import com.opalynskyi.cleanmovies.app.api.ServerMovie
import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity

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