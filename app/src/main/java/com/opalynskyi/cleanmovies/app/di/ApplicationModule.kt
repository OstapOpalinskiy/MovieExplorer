package com.opalynskyi.cleanmovies.app.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.opalynskyi.cleanmovies.app.AppSchedulerProvider
import com.opalynskyi.cleanmovies.app.DateTimeHelper
import com.opalynskyi.cleanmovies.app.api.MoviesApi
import com.opalynskyi.cleanmovies.app.database.DbConstants
import com.opalynskyi.cleanmovies.app.database.MoviesDao
import com.opalynskyi.cleanmovies.app.database.MoviesDatabase
import com.opalynskyi.cleanmovies.app.di.scopes.ApplicationScope
import com.opalynskyi.cleanmovies.core.SchedulerProvider
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @ApplicationScope
    fun provideContext() = context

    @Provides
    @ApplicationScope
    fun provideGson() = Gson()

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(MOVIES_APP_PREFS, Context.MODE_PRIVATE)

    @Provides
    fun provideApi(): MoviesApi = MoviesApi.create()

    @Provides
    @ApplicationScope
    fun provideDatabase(context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java, DbConstants.DB_NAME
        ).build()
    }

    @Provides
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao =
        moviesDatabase.moviesDao()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    @ApplicationScope
    fun provideDateTimeHelper() = DateTimeHelper()

    @Provides
    fun providePicasso(
        context: Context
    ): Picasso {
        val picasso = Picasso.Builder(context).build()
        Picasso.setSingletonInstance(picasso)
        return Picasso.get()
    }

    companion object {
        private const val MOVIES_APP_PREFS = "movies_app_prefs"
    }
}