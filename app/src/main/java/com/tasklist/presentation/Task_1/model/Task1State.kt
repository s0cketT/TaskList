package com.tasklist.presentation.Task_1.model

data class Task1State(
    val user: UserUiModel = UserUiModel(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
