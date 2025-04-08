package com.tasklist.domain.use_case

import com.tasklist.data.database.favorites.FavoritesTable
import com.tasklist.domain.repository.IPostFavoritesRepository
import kotlinx.coroutines.flow.Flow

class GetAllFavoritesUseCase(private val getAllFavoritesRepository: IPostFavoritesRepository) {
    suspend operator fun invoke(): Flow<List<FavoritesTable>> {
        return getAllFavoritesRepository.getAllFavorites()
    }
}
