package com.opalynskyi.cleanmovies.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.opalynskyi.cleanmovies.DateTimeHelper
import com.opalynskyi.cleanmovies.domain.DispatcherProvider
import com.opalynskyi.cleanmovies.data.api.MoviesApi
import com.opalynskyi.cleanmovies.data.database.DbConstants
import com.opalynskyi.cleanmovies.data.database.MoviesDao
import com.opalynskyi.cleanmovies.data.database.MoviesDatabase
import com.opalynskyi.cleanmovies.di.scopes.ApplicationScope
import com.opalynskyi.cleanmovies.presentation.imageLoader.ImageLoader
import com.opalynskyi.cleanmovies.presentation.imageLoader.PicassoImageLoader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers


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
    @ApplicationScope
    fun provideDateTimeHelper() = DateTimeHelper()

    @Provides
    @ApplicationScope
    fun provideImageLoader(
        context: Context
    ): ImageLoader {
        val picasso = Picasso.Builder(context).build()
        Picasso.setSingletonInstance(picasso)
        return PicassoImageLoader(Picasso.get())
    }

    @Provides
    fun provideDispatchersProvider(): DispatcherProvider {
        return object : DispatcherProvider {
            override fun default() = Dispatchers.Default
            override fun io() = Dispatchers.IO
            override fun main() = Dispatchers.Main
            override fun unconfined() = Dispatchers.Unconfined
        }
    }

    companion object {
        private const val MOVIES_APP_PREFS = "movies_app_prefs"
    }
}