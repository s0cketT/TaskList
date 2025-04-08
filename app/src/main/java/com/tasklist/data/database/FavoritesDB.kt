package com.tasklist.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tasklist.data.database.favorites.FavoritesDbModel
import com.tasklist.data.database.favorites.FavoritesDao

@Database(entities = [FavoritesDbModel::class], version = 1)
abstract class FavoritesDB : RoomDatabase() {
    abstract val daoFavorite: FavoritesDao
}