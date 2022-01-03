package com.opalynskyi.cleanmovies.presentation.adapter

data class MovieItem(
    val id: Int,
    val overview: String,
    val releaseDate: Long,
    val cover: String,
    val title: String,
    val rating: Float,
    val isFavourite: Boolean,
    val year: Int,
    val month: Int
): MoviesListItem