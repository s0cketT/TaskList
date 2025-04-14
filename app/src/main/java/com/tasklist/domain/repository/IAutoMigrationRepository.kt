package com.tasklist.domain.repository

import com.tasklist.domain.model.AutoMigrationDomainModel
import kotlinx.coroutines.flow.SharedFlow

interface IAutoMigrationRepository {

    val getAllAutoMigration: SharedFlow<List<AutoMigrationDomainModel>>
    suspend fun insertItem(item: AutoMigrationDomainModel)
    suspend fun deleteItem(item: AutoMigrationDomainModel)

}