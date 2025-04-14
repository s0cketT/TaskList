package com.tasklist.data.database.favorites

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tasklist.domain.model.AutoMigrationDomainModel

@Entity(tableName = "auto_migration")
data class AutoMigrationDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val B: String = "",
    val C: String = "",
    @ColumnInfo(defaultValue = "2")
    val D: String = ""
) {
    fun toDomainModel(): AutoMigrationDomainModel = AutoMigrationDomainModel(
        id = this.id,
        B = this.B,
        C = this.C,
        D = this.D,
    )

    companion object {
        fun fromDomainModel(domainModel: AutoMigrationDomainModel): AutoMigrationDbModel {
            return AutoMigrationDbModel(
                id = domainModel.id,
                B = domainModel.B,
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



