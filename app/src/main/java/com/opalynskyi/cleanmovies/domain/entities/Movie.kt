package com.opalynskyi.cleanmovies.domain.entities

data class Movie(
    val id: Int,

    var overview: String?,

    val releaseDateTimestamp: Long,

    val posterUrl: String,

    val title: String?,

    val rating: Float,

    var isFavourite: Boolean
)