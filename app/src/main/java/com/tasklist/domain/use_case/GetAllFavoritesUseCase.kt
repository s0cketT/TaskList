package com.tasklist.domain.use_case

import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.domain.repository.IPostFavoritesRepository
import kotlinx.coroutines.flow.SharedFlow

class GetAllFavoritesUseCase(private val getAllFavoritesRepository: IPostFavoritesRepository) {
     operator fun invoke(): SharedFlow<List<PostsDomainModel>> {
        return getAllFavoritesRepository.getAllFavorites
    }
}
