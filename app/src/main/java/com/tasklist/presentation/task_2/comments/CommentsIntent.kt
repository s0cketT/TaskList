package com.tasklist.presentation.task_2.comments

import com.tasklist.domain.model.PostsDomainModel

sealed interface CommentsIntent {
    data class IsFavorites(val post: PostsDomainModel) : CommentsIntent
}