package com.opalynskyi.core.domain.entities

data class Movie(
    val id: Int,
    var overview: String?,
    val releaseDateTimestamp: Long,
    val imageUrl: String,
    val title: String?,
    val rating: Float,
    var isFavourite: Boolean,
)
