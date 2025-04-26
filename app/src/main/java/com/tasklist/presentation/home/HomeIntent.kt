package com.tasklist.presentation.home


sealed interface HomeIntent {
    data class Navigate(val task: TaskScreen) : HomeIntent
}