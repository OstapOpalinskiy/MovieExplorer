package com.opalynskyi.cleanmovies.app.mainscreen.movies.adapter

enum class ItemType(val value: Int) {
    HEADER(0), ITEM(1);

    companion object {
        fun fromInt(value: Int): ItemType {
            return values().first { it.value == value }
        }
    }
}