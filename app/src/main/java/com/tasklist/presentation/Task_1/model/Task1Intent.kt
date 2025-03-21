package com.tasklist.presentation.Task_1.model

sealed interface Task1Intent {
    data class EnterLogin(val login: String) : Task1Intent
    data class EnterPassword(val password: String) : Task1Intent
    data object SubmitLogin : Task1Intent
    data object TogglePasswordVisibility : Task1Intent
}
