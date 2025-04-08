package com.tasklist.data.repository

import com.tasklist.data.database.favorites.FavoritesDao
import com.tasklist.data.database.favorites.FavoritesTable
import com.tasklist.domain.repository.IPostFavoritesRepository
import kotlinx.coroutines.flow.Flow

class PostFavoritesLocalRepositoryImpl(private val dao: FavoritesDao) : IPostFavoritesRepository {

    override suspend fun getAllFavorites(): Flow<List<FavoritesTable>> {
        return dao.getAllFavorites()
    }

    override suspend fun insertFavorites(favoritesTable: FavoritesTable) {
        dao.insert(favoritesTable)
    }

    override suspend fun deleteFavorites(favoritesTable: FavoritesTable) {
        dao.delete(favoritesTable)
    }

    override suspend fun isFavorite(postId: Int): Boolean {
        return dao.isFavorite(postId)
    }
}