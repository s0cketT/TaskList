package com.tasklist.presentation.task_2.post_api.model

import com.tasklist.data.database.favorites.FavoritesTable
import com.tasklist.domain.model.PostsDomainModel

data class PostState(
    val isLoading: Boolean = false,
    val originalPosts: List<PostsDomainModel> = emptyList(),
    val error: Int? = null,
    val textField: String = "",
    val favorites: List<FavoritesTable> = emptyList(),
) {
    val posts: List<PostsDomainModel> get() = filterPosts(originalPosts, textField).sortedByDescending { post -> isPostFavorite(post) }

    fun isPostFavorite(post: PostsDomainModel): Boolean {
        return favorites.any { it.id == post.id }
    }

    companion object {
        fun filterPosts(originalPosts: List<PostsDomainModel>, query: String): List<PostsDomainModel> {
            if (query.isBlank()) return originalPosts
            return originalPosts.filter { post ->
                post.title.contains(query, ignoreCase = true) || post.body.contains(query, ignoreCase = true)
            }
        }
    }
}


