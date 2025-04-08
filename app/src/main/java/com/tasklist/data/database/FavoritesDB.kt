package com.tasklist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tasklist.data.database.favorites.FavoritesDao
import com.tasklist.data.database.favorites.FavoritesTable

@Database(entities = [FavoritesTable::class], version = 1)
abstract class FavoritesDB : RoomDatabase() {
    abstract val daoFavorite: FavoritesDao
}