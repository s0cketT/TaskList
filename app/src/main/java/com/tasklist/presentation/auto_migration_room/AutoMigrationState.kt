package com.tasklist.presentation.auto_migration_room

import com.tasklist.domain.model.AutoMigrationDomainModel

data class AutoMigrationState(
    val autoMigrationList: List<AutoMigrationDomainModel> = emptyList()
)
