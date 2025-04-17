package com.tasklist.presentation.task_pages

sealed interface TaskPagesIntent {
    data object LoadingNextPages : TaskPagesIntent
}