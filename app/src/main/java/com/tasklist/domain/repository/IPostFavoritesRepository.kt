package com.tasklist.domain.repository

import com.tasklist.domain.model.PostsDomainModel

import kotlinx.coroutines.flow.SharedFlow

interface IPostFavoritesRepository {

    val getAllFavorites: SharedFlow<List<PostsDomainModel>>
    suspend fun insertFavorites(post: PostsDomainModel)
    suspend fun deleteFavorites(post: PostsDomainModel)

}