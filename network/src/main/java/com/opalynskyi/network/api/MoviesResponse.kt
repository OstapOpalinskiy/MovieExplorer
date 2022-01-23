package com.opalynskyi.network.api

import com.google.gson.annotations.SerializedName


class MoviesResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("results")
    val movies: List<ServerMovie>?,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("total_results")
    var totalResults: Int
)