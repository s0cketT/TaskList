package com.tasklist.domain.use_case

import com.tasklist.data.database.favorites.FavoritesTable
import com.tasklist.domain.repository.IPostFavoritesRepository

class InsertFavoritesUseCase(private val insertFavoritesRepository: IPostFavoritesRepository) {
    suspend operator fun invoke(favorites: FavoritesTable) {
        insertFavoritesRepository.insertFavorites(favorites)
    }
}