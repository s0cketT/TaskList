package com.tasklist.presentation.task_2.comments

import com.tasklist.domain.model.CommentsDomainModel


data class CommentsState(
    val isLoading: Boolean = false,
    val comments: List<CommentsDomainModel> = emptyList(),
    val error: String? = null
)

