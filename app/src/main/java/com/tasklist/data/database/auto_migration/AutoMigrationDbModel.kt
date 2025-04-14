package com.tasklist.data.database.favorites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tasklist.domain.model.AutoMigrationDomainModel

@Entity(tableName = "auto_migration")
data class AutoMigrationDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val W: String = "",
    val C: String = "",
    @ColumnInfo(defaultValue = "2")
    val D: String = ""
) {
    fun toDomainModel(): AutoMigrationDomainModel = AutoMigrationDomainModel(
        id = this.id,
        W = this.W,
        C = this.C,
        D = this.D,
    )

    companion object {
        fun fromDomainModel(domainModel: AutoMigrationDomainModel): AutoMigrationDbModel {
            return AutoMigrationDbModel(
                id = domainModel.id,
                W = domainModel.W,
                C = domainModel.C,
                D = domainModel.D,
            )
        }

        fun List<AutoMigrationDomainModel>.fromDomainModelList(): List<AutoMigrationDbModel> {
            return this.map { fromDomainModel(it) }
        }

        fun List<AutoMigrationDbModel>.toDomainModelList(): List<AutoMigrationDomainModel> {
            return this.map { it.toDomainModel() }
        }
    }
}



