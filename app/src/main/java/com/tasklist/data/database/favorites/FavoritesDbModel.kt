package com.tasklist.data.database.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tasklist.domain.model.PostsDomainModel

@Entity(tableName = "favorites")
data class FavoritesDbModel(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
) {
    fun toDomainModel(): PostsDomainModel = PostsDomainModel(
        id = this.id,
        userId = this.userId,
        title = this.title,
        body = this.body,
        isFavorite = true
    )

    companion object {
        fun fromDomainModel(domainModel: PostsDomainModel): FavoritesDbModel {
            return FavoritesDbModel(
                id = domainModel.id,
                userId = domainModel.userId,
                title = domainModel.title,
                body = domainModel.body
            )
        }

        fun List<PostsDomainModel>.fromDomainModelList(): List<FavoritesDbModel> {
            return this.map { fromDomainModel(it) }
        }

        fun List<FavoritesDbModel>.toDomainModelList(): List<PostsDomainModel> {
            return this.map { it.toDomainModel() }
        }
    }
}



