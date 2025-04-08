package com.tasklist.domain.use_case

import com.tasklist.domain.common.Resource
import com.tasklist.domain.model.ExceptionDomainModel
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.domain.repository.IPostFavoritesRepository
import com.tasklist.domain.repository.IPostsRepository
import kotlinx.coroutines.flow.firstOrNull

class GetPostsUseCase(
    private val postRepository: IPostsRepository,
    private val favoritesRepository: IPostFavoritesRepository
    ) {
    suspend operator fun invoke(): Resource<List<PostsDomainModel>, ExceptionDomainModel> {

        return when (val result = postRepository.getPosts()) {
            is Resource.Success -> {
                val favorites = favoritesRepository.getAllFavorites.firstOrNull().orEmpty().map { it.id }

                val updatedList = result.data.map {
                    it.copy(isFavorite = it.id in favorites)
                }
                Resource.Success(updatedList)
            }

            is Resource.Error -> result
        }
    }
}
