package com.opalynskyi.cleanmovies.app.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApi {

    @GET("discover/movie")
    fun getOngoingMovies(
        @Query("primary_release_date.gte") startDate: String,
        @Query("primary_release_date.lte") endDate: String
    ): Single<MoviesResponse>
}