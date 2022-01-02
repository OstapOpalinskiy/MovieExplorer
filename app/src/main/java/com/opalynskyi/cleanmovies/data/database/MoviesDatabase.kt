package com.opalynskyi.cleanmovies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [MovieDbEntity::class], version = DbConstants.DB_VERSION, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}