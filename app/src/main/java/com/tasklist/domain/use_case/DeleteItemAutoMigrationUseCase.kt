package com.tasklist.domain.use_case

import com.tasklist.domain.model.AutoMigrationDomainModel
import com.tasklist.domain.repository.IAutoMigrationRepository

class DeleteItemAutoMigrationUseCase(private val autoMigrationRepository: IAutoMigrationRepository) {
    suspend operator fun invoke(item: AutoMigrationDomainModel) {
        autoMigrationRepository.deleteItem(item)
    }
}
