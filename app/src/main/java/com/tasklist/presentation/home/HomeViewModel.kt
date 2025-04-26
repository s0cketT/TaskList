package com.tasklist.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasklist.presentation.Navigation.SingleFlowEvent
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {


    private val _event = SingleFlowEvent<HomeEvent>(viewModelScope)
    val event = _event.flow

    fun processIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.Navigate -> { viewModelScope.launch {
                _event.emit(HomeEvent.Navigate(intent.task))
                }
            }
        }
    }

}
