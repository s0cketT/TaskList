package com.tasklist.domain.use_case

import com.tasklist.domain.repository.IPostFavoritesRepository

class IsPostFavoritesUseCase(private val isFavorites: IPostFavoritesRepository) {
    suspend operator fun invoke(postId: Int): Boolean {
        return isFavorites.isFavorite(postId)
    }
}
