package com.tasklist.presentation.Task_1.model

sealed class Task1Intent {
    data class EnterLogin(val login: String) : Task1Intent()
    data class EnterPassword(val password: String) : Task1Intent()
    data object SubmitLogin : Task1Intent()
    data object ResetState : Task1Intent()
}
