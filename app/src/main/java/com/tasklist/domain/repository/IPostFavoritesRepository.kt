package com.tasklist.domain.repository

import com.tasklist.data.database.favorites.FavoritesTable
import kotlinx.coroutines.flow.Flow

interface IPostFavoritesRepository {

    suspend fun getAllFavorites(): Flow<List<FavoritesTable>>
    suspend fun insertFavorites(favoritesTable: FavoritesTable)
    suspend fun deleteFavorites(favoritesTable: FavoritesTable)
    suspend fun isFavorite(postId: Int): Boolean

}