package com.tasklist.mock

import com.tasklist.data.database.favorites.FavoritesDao
import com.tasklist.data.database.favorites.FavoritesDbModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.koin.dsl.module

class FavoritesDao_Mock {

    companion object {
        val initFavoritePost = (0..5).map {
            FavoritesDbModel(
                id = it,
                userId = it,
                title = it.toString(),
                body = it.toString()
            )
        }
    }

    fun mModule(
        initDbList: List<FavoritesDbModel> = initFavoritePost
    ) = module {
        val _posts = MutableStateFlow(initDbList)

        single<FavoritesDao> {
            object : FavoritesDao {
                override suspend fun insert(favorites: FavoritesDbModel) {
                    _posts.update {
                        it.toMutableList().apply {
                            replaceAll {
                                if (it.id == favorites.id) {
                                    favorites
                                } else it
                            }
                        }
                    }
                }

                override suspend fun delete(favorites: FavoritesDbModel) {
                    _posts.update {
                        it.toMutableList().apply {
                            removeIf {
                                it.id == favorites.id
                            }
                        }
                    }
                }

                override fun getAllFavorites(): Flow<List<FavoritesDbModel>> = _posts
            }
        }
    }
}