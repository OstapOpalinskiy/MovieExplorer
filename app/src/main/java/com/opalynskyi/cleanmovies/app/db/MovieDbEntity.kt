package com.opalynskyi.cleanmovies.app.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDbEntity(

    @PrimaryKey
    val id: Int,

    var overview: String?,

    val releaseDate: String,

    val posterPath: String,

    val title: String?,

    val voteAverage: Float,

    var isFavourite: Boolean
)