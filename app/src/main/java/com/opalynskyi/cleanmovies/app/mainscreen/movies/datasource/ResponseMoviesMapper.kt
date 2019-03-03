package com.opalynskyi.cleanmovies.app.mainscreen.movies.datasource

import com.opalynskyi.cleanmovies.app.api.MovieResponce
import com.opalynskyi.cleanmovies.core.data.movies.entities.MovieEntity

class ResponseMoviesMapper {

    fun mapFromEntity(entity: MovieResponce) = MovieEntity(
        entity.id,
        entity.overview,
        entity.releaseDate,
        entity.posterPath,
        entity.title,
        entity.voteAverage,
        false
    )
}