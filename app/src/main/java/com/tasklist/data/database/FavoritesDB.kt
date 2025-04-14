package com.tasklist.data.database


import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.DeleteTable
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.tasklist.data.database.favorites.FavoritesDao
import com.tasklist.data.database.favorites.FavoritesDbModel

@Database(
    entities = [FavoritesDbModel::class],
    version = 6,
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
        ),
        AutoMigration(
            from = 5,
            to = 6,
            spec = FavoritesDB.AutoMigrationFrom5To6::class
        )
    ],
    exportSchema = true
)
abstract class FavoritesDB : RoomDatabase() {
    abstract val daoFavorite: FavoritesDao

    @DeleteColumn(tableName = "auto_migration", columnName = "A")
    class AutoMigrationFrom3To4 : AutoMigrationSpec

    @RenameColumn(tableName = "auto_migration", fromColumnName = "B", toColumnName = "W")
    class AutoMigrationFrom4To5 : AutoMigrationSpec

    @DeleteTable(tableName = "auto_migration")
    class AutoMigrationFrom5To6 : AutoMigrationSpec
}
