package com.tasklist.presentation.task_2.post_api.model

import com.tasklist.domain.model.PostsDomainModel

data class PostState(
    val isLoading: Boolean = false,
    val originalPosts: List<PostsDomainModel> = emptyList(),
    val error: Int? = null,
    val textField: String = ""
) {
    val posts: List<PostsDomainModel> = filterPosts(originalPosts, textField).sortedByDescending { post -> post.isFavorite }

    companion object {
        fun filterPosts(originalPosts: List<PostsDomainModel>, query: String): List<PostsDomainModel> {
            if (query.isBlank()) return originalPosts
            return originalPosts.filter { post ->
                post.title.contains(query, ignoreCase = true) || post.body.contains(query, ignoreCase = true)
            }
        }
    }
}


