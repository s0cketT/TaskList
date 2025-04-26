package com.tasklist.presentation.compose_distination

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SharedViewModel(): ViewModel() {

    init {
        viewModelScope.launch {
                var counter = 0
                while (true) {
                    Log.d("!!!", "$counter")
                    counter++
                    delay(1000)
                }
        }
    }

}