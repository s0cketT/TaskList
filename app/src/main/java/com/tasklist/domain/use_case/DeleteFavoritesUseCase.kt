package com.tasklist.domain.use_case

import com.tasklist.data.database.favorites.FavoritesTable
import com.tasklist.domain.repository.IPostFavoritesRepository

class DeleteFavoritesUseCase(private val deleteFavoritesRepository: IPostFavoritesRepository) {
    suspend operator fun invoke(favorites: FavoritesTable) {
        deleteFavoritesRepository.deleteFavorites(favorites)
    }
}