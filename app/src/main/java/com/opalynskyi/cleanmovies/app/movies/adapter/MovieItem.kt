package com.opalynskyi.cleanmovies.app.movies.adapter

import com.opalynskyi.cleanmovies.core.domain.movies.entities.Movie

class MovieItem(
    val overview: String?,

    val releaseDate: String,

    val posterPath: String,

    val title: String?,

    val voteAverage: Float,

    var isFavourite: Boolean,

    itemType: ItemType

) : ListItem(itemType) {
    companion object {
        fun fromMovie(movie: Movie) = MovieItem(
            movie.overview,
            movie.releaseDate,
            movie.posterPath,
            movie.title,
            movie.voteAverage,
            movie.isFavourite,
            ItemType.ITEM
        )
    }
}