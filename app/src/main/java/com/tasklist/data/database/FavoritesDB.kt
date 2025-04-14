package com.tasklist.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.tasklist.data.database.favorites.AutoMigrationDao
import com.tasklist.data.database.favorites.AutoMigrationDbModel
import com.tasklist.data.database.favorites.FavoritesDbModel
import com.tasklist.data.database.favorites.FavoritesDao

@Database(
    entities = [FavoritesDbModel::class, AutoMigrationDbModel::class],
    version = 5,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3),
        AutoMigration(
            from = 3,
            to = 4,
            spec = FavoritesDB.AutoMigrationFrom3To4::class
        ),
        AutoMigration(
            from = 4,
            to = 5,
            spec = FavoritesDB.AutoMigrationFrom4To5::class
        )
    ],
    exportSchema = true
)
abstract class FavoritesDB : RoomDatabase() {
    abstract val daoFavorite: FavoritesDao
    abstract val autoMigrationDao: AutoMigrationDao

    @DeleteColumn(tableName = "auto_migration", columnName = "A")
    class AutoMigrationFrom3To4 : AutoMigrationSpec

    @RenameColumn(tableName = "auto_migration", fromColumnName = "B", toColumnName = "W")
    class AutoMigrationFrom4To5 : AutoMigrationSpec
}
