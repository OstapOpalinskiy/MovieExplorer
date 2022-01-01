package com.opalynskyi.cleanmovies.app.domain.entities

data class MovieEntity(
    val id: Int,

    var overview: String?,

    val releaseDate: Long,

    val posterPath: String,

    val title: String?,

    val voteAverage: Float,

    var isFavourite: Boolean
)