package com.tasklist.data.database.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tasklist.domain.model.AutoMigrationDomainModel

@Entity(tableName = "auto_migration")
data class AutoMigrationDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val A: String = "",
    val B: String = "",
    val C: String = ""
) {
    fun toDomainModel(): AutoMigrationDomainModel = AutoMigrationDomainModel(
        id = this.id,
        A = this.A,
        B = this.B,
        C = this.C,
    )

    companion object {
        fun fromDomainModel(domainModel: AutoMigrationDomainModel): AutoMigrationDbModel {
            return AutoMigrationDbModel(
                id = domainModel.id,
                A = domainModel.A,
                B = domainModel.B,
                C = domainModel.C
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



