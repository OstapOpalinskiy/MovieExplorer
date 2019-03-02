package com.opalynskyi.cleanmovies.app.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.opalynskyi.cleanmovies.app.AppSchedulerProvider
import com.opalynskyi.cleanmovies.app.api.ApiConstants
import com.opalynskyi.cleanmovies.app.api.MoviesApi
import com.opalynskyi.cleanmovies.app.api.RequestInterceptor
import com.opalynskyi.cleanmovies.app.db.DbConstants
import com.opalynskyi.cleanmovies.app.db.MoviesDao
import com.opalynskyi.cleanmovies.app.db.MoviesDatabase
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Singleton


@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_USER, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideLogRetrofit(
        baseUrl: HttpUrl,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRxJava2Adapter(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()


    @Provides
    @Singleton
    fun provideBaseUrl(): HttpUrl = HttpUrl.parse(ApiConstants.BASE_URL)
        ?: throw UnknownHostException("Invalid host: " + ApiConstants.BASE_URL)

    @Provides
    @Singleton
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)

    @Provides
    @Singleton
    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        requestInterceptor: RequestInterceptor
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(requestInterceptor)
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    internal fun provideLogHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.HEADERS
        return logging
    }

    @Provides
    @Singleton
    fun provideRequestInterceptor(): RequestInterceptor = RequestInterceptor()

    @Provides
    @Singleton
    fun getDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java, DbConstants.DB_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun gprovideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao =
        moviesDatabase.moviesDao()

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    companion object {
        private const val PREFS_USER = "prefs_user"
    }
}