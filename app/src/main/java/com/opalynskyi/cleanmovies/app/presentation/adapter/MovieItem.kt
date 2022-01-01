package com.opalynskyi.cleanmovies.app.presentation.adapter

class MovieItem(
    val id: Int,

    val overview: String,

    val releaseDate: Long,

    val cover: String,

    val title: String,

    val rating: Float,

    var isFavourite: Boolean,

    val year: Int,

    val month: Int
)