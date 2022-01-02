package com.opalynskyi.cleanmovies.data.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApi {

    @GET("discover/movie")
    fun getOngoingMovies(
        @Query("primary_release_date.gte") startDate: String,
        @Query("primary_release_date.lte") endDate: String
    ): Single<MoviesResponse>

    @GET("discover/movie")
    suspend fun getOngoingMoviesSync(
        @Query("primary_release_date.gte") startDate: String,
        @Query("primary_release_date.lte") endDate: String
    ): MoviesResponse

    companion object {
        private val httpClient by lazy {
            OkHttpClient.Builder().apply {
                addInterceptor(RequestInterceptor())
                addInterceptor(HttpLoggingInterceptor())
            }.build()
        }

        fun create(): MoviesApi {
            return Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build().create(MoviesApi::class.java)
        }
    }
}