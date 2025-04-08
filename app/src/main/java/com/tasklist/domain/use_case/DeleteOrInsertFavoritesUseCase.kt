package com.tasklist.domain.use_case

import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.domain.repository.IPostFavoritesRepository

class DeleteOrInsertFavoritesUseCase(private val favoritesRepository: IPostFavoritesRepository) {
    suspend operator fun invoke(post: PostsDomainModel) {
        if (post.isFavorite) {
            favoritesRepository.deleteFavorites(post)
        } else {
            favoritesRepository.insertFavorites(post)
        }

    }
}
