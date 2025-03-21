package com.tasklist.presentation.Task_1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasklist.presentation.Navigation.SingleFlowEvent

import com.tasklist.domain.use_case.ValidateUserUseCase
import com.tasklist.presentation.Task_1.model.Task1Intent
import com.tasklist.presentation.Task_1.model.Task1State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Task1ViewModel(private val validateUserUseCase: ValidateUserUseCase) : ViewModel() {

    private val _state = MutableStateFlow(Task1State())
    val state = _state.asStateFlow()

    sealed interface Event {
        class NavigateToNextScreen : Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    fun processIntent(intent: Task1Intent) {
        when (intent) {
            is Task1Intent.EnterLogin -> _state.update {
                it.copy(
                    user = it.user.copy(login = intent.login),
                    errorMessage = null,
                    buttonActivity = intent.login.isNotBlank() && it.user.password.isNotBlank()
                )
            }
            is Task1Intent.EnterPassword -> _state.update {
                it.copy(
                    user = _state.value.user.copy(password = intent.password),
                    errorMessage = null,
                    buttonActivity = it.user.login.isNotBlank() && intent.password.isNotBlank()
                )
            }
            is Task1Intent.SubmitLogin -> {
                attemptLogin()
            }

            Task1Intent.TogglePasswordVisibility -> _state.update {
                it.copy(passwordVisible = !it.passwordVisible)
            }


        }
    }

    private fun attemptLogin() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, buttonActivity = false)

            val isValid = withContext(Dispatchers.IO) {
                validateUserUseCase(_state.value.user.login, _state.value.user.password)
            }

            if (isValid) {
                _event.emit(Event.NavigateToNextScreen())
            } else {
                _state.value = _state.value.copy(errorMessage = "Неверный логин или пароль")
            }

            _state.value = _state.value.copy(isLoading = false, buttonActivity = true)
        }
    }
}




