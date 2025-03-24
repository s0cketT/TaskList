package com.tasklist.presentation.Task_1

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.InvalidCredentialsException

import com.tasklist.domain.use_case.ValidateUserUseCase
import com.tasklist.presentation.Navigation.Screens
import com.tasklist.presentation.Task_1.model.Task1Intent
import com.tasklist.presentation.Task_1.model.Task1State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Task1ViewModel(private val validateUserUseCase: ValidateUserUseCase) : ViewModel() {

    private val _state = MutableStateFlow(Task1State())
    val state: StateFlow<Task1State> = _state

    fun processIntent(intent: Task1Intent, navController: NavController) {
        when (intent) {
            is Task1Intent.EnterLogin -> {
                _state.value = _state.value.copy(
                    user = _state.value.user.copy(login = intent.login),
                    errorMessage = null
                )
            }
            is Task1Intent.EnterPassword -> {
                _state.value = _state.value.copy(
                    user = _state.value.user.copy(password = intent.password),
                    errorMessage = null
                )
            }
            is Task1Intent.SubmitLogin -> {
                attemptLogin(navController)
            }
            is Task1Intent.ResetState -> {
                _state.value = Task1State()
            }
        }
    }

    private fun attemptLogin(navController: NavController) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val isValid = validateUserUseCase(
                _state.value.user.login,
                _state.value.user.password
            )

            if (isValid) {
                navController.navigate(Screens.Task1Success.route)
            } else {
                _state.value = _state.value.copy(isLoading = false, errorMessage = "Неверный логин или пароль")
            }
        }
    }
}




