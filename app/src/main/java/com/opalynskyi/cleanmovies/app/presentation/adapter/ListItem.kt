package com.opalynskyi.cleanmovies.app.presentation.adapter

data class ListItem(
    val type: ItemType,
    val headerTitle: String? = null,
    val movie: MovieItem? = null,
    val children: MutableList<ListItem> = mutableListOf(),
    val header: ListItem? = null
)