package com.tasklist.presentation.auto_migration_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasklist.domain.use_case.DeleteItemAutoMigrationUseCase
import com.tasklist.domain.use_case.GetAllAutoMigrationUseCase
import com.tasklist.domain.use_case.InsertItemAutoMigrationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AutoMigrationViewModel(
    private val getAutoMigrationState: GetAllAutoMigrationUseCase,
    private val insertItemAutoMigrationUseCase: InsertItemAutoMigrationUseCase,
    private val deleteItemAutoMigrationUseCase: DeleteItemAutoMigrationUseCase
): ViewModel() {

    private val _state = MutableStateFlow(AutoMigrationState())
    val state = _state.asStateFlow()

    init {
        getAllItems()
    }

    fun processIntent(intent: AutoMigrationIntent) {
        when (intent) {
            is AutoMigrationIntent.DeleteItem -> viewModelScope.launch {
                deleteItemAutoMigrationUseCase(intent.item)
            }
            is AutoMigrationIntent.InsertItem -> viewModelScope.launch {
                insertItemAutoMigrationUseCase(intent.item)
            }
        }
    }

    private fun getAllItems() {
        viewModelScope.launch {
            getAutoMigrationState().collect { items ->
                _state.update { it.copy(autoMigrationList = items) }
            }
        }
    }

}