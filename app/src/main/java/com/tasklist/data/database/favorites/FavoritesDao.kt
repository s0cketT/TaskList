package com.tasklist.data.database.favorites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorites: FavoritesDbModel)

    @Delete
    suspend fun delete(favorites: FavoritesDbModel)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoritesDbModel>>

}