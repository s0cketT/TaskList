package com.tasklist.presentation.home


sealed interface HomeEvent {
    data class Navigate(val destination: TaskScreen) : HomeEvent
}