package com.tasklist.presentation.task_2.post_api.model

import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.presentation.Task_1.model.Task1Intent

sealed interface PostIntent {

    data class EnterTextField(val textField: String) : PostIntent
    data class NavigateToCommentsScreen(val post: PostsDomainModel): PostIntent
}