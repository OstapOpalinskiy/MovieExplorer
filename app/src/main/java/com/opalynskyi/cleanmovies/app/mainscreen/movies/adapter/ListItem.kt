package com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter

data class ListItem(
    val type: ItemType,
    val headerTitle: String? = null,
    val movie: MovieItem? = null,
    val children: MutableList<ListItem> = mutableListOf(),
    val header: ListItem? = null
)