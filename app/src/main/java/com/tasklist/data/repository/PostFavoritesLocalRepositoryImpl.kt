package com.tasklist.data.repository

import com.tasklist.data.database.favorites.FavoritesDao
import com.tasklist.data.database.favorites.FavoritesDbModel
import com.tasklist.data.database.favorites.FavoritesDbModel.Companion.toDomainModelList
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.domain.repository.IPostFavoritesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.plus

class PostFavoritesLocalRepositoryImpl(private val dao: FavoritesDao) : IPostFavoritesRepository {

    override val getAllFavorites: SharedFlow<List<PostsDomainModel>> = dao.getAllFavorites()
        .map { it.toDomainModelList() }
        .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .distinctUntilChanged()
        .shareIn(
            scope = MainScope() + Dispatchers.IO,
            started = SharingStarted.WhileSubscribed(10_000, replayExpirationMillis = 0),
            replay = 1
        )

    override suspend fun insertFavorites(post: PostsDomainModel) {
        dao.insert(FavoritesDbModel.fromDomainModel(post))
    }

    override suspend fun deleteFavorites(post: PostsDomainModel) {
        dao.delete(FavoritesDbModel.fromDomainModel(post))
    }
}