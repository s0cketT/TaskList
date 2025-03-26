package com.tasklist.presentation.home

import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    fun getTasks(): List<String> {
        return listOf("Task1", "Task2-PostApi")
    }

}