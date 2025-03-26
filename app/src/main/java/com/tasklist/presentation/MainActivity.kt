package com.tasklist.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.tasklist.presentation.Navigation.components.AppUI
import com.tasklist.presentation.ui.theme.TaskListTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskListTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValue ->
                    Box(modifier = Modifier.fillMaxSize().padding(paddingValue)) {
                        AppUI()
                    }

                }
            }
        }
    }
}



