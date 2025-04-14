package com.tasklist.presentation.auto_migration_room

import com.tasklist.domain.model.AutoMigrationDomainModel

sealed interface AutoMigrationIntent {
    data class InsertItem(val item: AutoMigrationDomainModel) : AutoMigrationIntent
    data class DeleteItem(val item: AutoMigrationDomainModel) : AutoMigrationIntent
}