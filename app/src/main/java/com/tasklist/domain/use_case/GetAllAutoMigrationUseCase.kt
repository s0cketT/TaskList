package com.tasklist.domain.use_case

import com.tasklist.domain.model.AutoMigrationDomainModel
import com.tasklist.domain.repository.IAutoMigrationRepository
import kotlinx.coroutines.flow.SharedFlow

class GetAllAutoMigrationUseCase(private val autoMigrationRepository: IAutoMigrationRepository) {
     operator fun invoke(): SharedFlow<List<AutoMigrationDomainModel>> {
        return autoMigrationRepository.getAllAutoMigration
    }
}
