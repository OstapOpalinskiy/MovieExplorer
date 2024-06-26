package com.opalynskyi.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Query("SELECT * FROM MovieDbEntity")
    fun getAll(): List<MovieDbEntity>

    @Query("SELECT * FROM MovieDbEntity")
    fun getAllFlow(): Flow<List<MovieDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: MovieDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieDbEntity)

    @Query("SELECT * FROM MovieDbEntity where id = :id")
    fun getById(id: Int): MovieDbEntity

    @Query("SELECT * FROM MovieDbEntity WHERE isFavourite = 1")
    fun getFavourite(): List<MovieDbEntity>

    @Update
    fun update(movie: MovieDbEntity): Int

    @Delete
    fun delete(movie: MovieDbEntity): Int
}
