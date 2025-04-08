package com.tasklist.presentation.task_2.post_api.model

import com.tasklist.domain.model.PostsDomainModel

sealed interface PostIntent {
    data class EnterTextField(val textField: String) : PostIntent
    data class NavigateToCommentsScreen(val post: PostsDomainModel): PostIntent

    data class IsFavorites(val post: PostsDomainModel) : PostIntent


}