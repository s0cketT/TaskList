package com.tasklist.presentation.task_pages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasklist.domain.use_case.getMyDataPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskPagesViewModel : ViewModel() {
    private val _state = MutableStateFlow(TaskPagesState())
    val state = _state.asStateFlow()

    init {
        loadPage(state.value.currentPage)
    }

    private fun loadPage(page: Int) {
        viewModelScope.launch {
            runCatching {
                _state.update { it.copy(isAppending = true) }

                while (true) {
                    val result = getMyDataPage(page)

                    result.onSuccess { dataPage ->
                        _state.update {
                            it.copy(
                                isAppending = false,
                                currentPage = page,
                                maxPage = dataPage.maxPage,
                                pageSize = dataPage.data.size,
                                data = it.data + dataPage.data
                            )
                        }
                        return@launch
                    }
                }
            }
        }
    }

    fun processIntent(intent: TaskPagesIntent) {
        when (intent) {
            is TaskPagesIntent.LoadingNextPages -> {
                if (!state.value.isAppending && state.value.currentPage < state.value.maxPage) {
                    loadPage(state.value.currentPage + 1)
                }
            }
        }
    }
}
