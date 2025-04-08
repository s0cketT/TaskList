package com.tasklist.data.database.favorites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorites: FavoritesTable)

    @Delete
    suspend fun delete(favorites: FavoritesTable)

    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Flow<List<FavoritesTable>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean


}