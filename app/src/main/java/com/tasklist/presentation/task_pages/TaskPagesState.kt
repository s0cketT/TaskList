package com.tasklist.presentation.task_pages

data class TaskPagesState(
    val isAppending: Boolean = false,
    val data: List<String> = emptyList(),
    val currentPage: Int = 1,
    val maxPage: Int = 0,
    val pageSize: Int = 0
)



