package com.tasklist.data.database.favorites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AutoMigrationDao{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: AutoMigrationDbModel)

    @Delete
    suspend fun delete(item: AutoMigrationDbModel)

    @Query("SELECT * FROM auto_migration")
    fun getAllAutoMigration(): Flow<List<AutoMigrationDbModel>>

}