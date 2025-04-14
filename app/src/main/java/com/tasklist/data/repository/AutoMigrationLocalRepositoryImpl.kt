package com.tasklist.data.repository

import com.tasklist.data.database.favorites.AutoMigrationDao
import com.tasklist.data.database.favorites.AutoMigrationDbModel
import com.tasklist.data.database.favorites.AutoMigrationDbModel.Companion.toDomainModelList
import com.tasklist.domain.model.AutoMigrationDomainModel
import com.tasklist.domain.repository.IAutoMigrationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.plus

class AutoMigrationLocalRepositoryImpl(private val dao: AutoMigrationDao) : IAutoMigrationRepository {

    override val getAllAutoMigration: SharedFlow<List<AutoMigrationDomainModel>> = dao.getAllAutoMigration()
        .map { it.toDomainModelList() }
        .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .distinctUntilChanged()
        .shareIn(
            scope = MainScope() + Dispatchers.IO,
            started = SharingStarted.WhileSubscribed(10_000, replayExpirationMillis = 0),
            replay = 1
        )

    override suspend fun insertItem(item: AutoMigrationDomainModel) {
        dao.insert(AutoMigrationDbModel.fromDomainModel(item))
    }

    override suspend fun deleteItem(item: AutoMigrationDomainModel) {
        dao.delete(AutoMigrationDbModel.fromDomainModel(item))
    }
}