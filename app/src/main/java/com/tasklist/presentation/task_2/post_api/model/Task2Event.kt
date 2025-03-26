package com.tasklist.presentation.task_2.post_api.model

import com.tasklist.domain.model.PostsDomainModel


sealed interface Task2Event {
    class NavigateToCommentsScreen(val post: PostsDomainModel): Task2Event
}