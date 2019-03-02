package com.opalynskyi.cleanmovies.core.domain.movies.entities

data class Movie(
    val id: Int,

    var overview: String?,

    val releaseDate: String,

    val posterPath: String,

    val title: String?,

    val voteAverage: Float,

    var isFavourite: Boolean
)