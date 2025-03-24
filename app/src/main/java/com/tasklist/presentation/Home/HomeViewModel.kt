package com.tasklist.presentation.Home

import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    fun getTasks(): List<String> {
        return listOf("Task1")
    }

}