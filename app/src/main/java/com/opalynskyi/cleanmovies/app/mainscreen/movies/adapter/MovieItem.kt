package com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter

class MovieItem(
    val overview: String,

    val releaseDate: Long,

    val cover: String,

    val title: String,

    val rating: Float,

    var isFavourite: Boolean,

    val year: Int,

    val month: Int,

    itemType: ItemType

) : ListItem(itemType)