package com.tasklist.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.tasklist.data.database.favorites.AutoMigrationDao
import com.tasklist.data.database.favorites.AutoMigrationDbModel
import com.tasklist.data.database.favorites.FavoritesDbModel
import com.tasklist.data.database.favorites.FavoritesDao

@Database(
    entities = [FavoritesDbModel::class, AutoMigrationDbModel::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ],
    exportSchema = true
)
abstract class FavoritesDB : RoomDatabase() {
    abstract val daoFavorite: FavoritesDao
    abstract val autoMigrationDao: AutoMigrationDao
}
