package com.opalynskyi.network.api

import com.google.gson.annotations.SerializedName


class ServerMovie(
    @SerializedName("id")
    val id: Int,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("genre_ids")
    val genreIds: IntArray? = IntArray(0),

    @SerializedName("original_language")
    val originalLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("overview")
    var overview: String?,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("poster_path")
    val posterPath: String,

    @SerializedName("popularity")
    val popularity: Float,

    @SerializedName("title")
    val title: String?,

    @SerializedName("video")
    val isVideo: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Float,

    @SerializedName("vote_count")
    val voteCount: Int
)