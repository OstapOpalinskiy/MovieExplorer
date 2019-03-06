package com.opalynskyi.cleanmovies.core.movies.domain.entities

data class Movie(
    val id: Int,

    var overview: String?,

    val releaseDate: Long,

    val posterPath: String,

    val title: String?,

    val voteAverage: Float,

    var isFavourite: Boolean
)