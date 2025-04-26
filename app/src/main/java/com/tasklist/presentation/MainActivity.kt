package com.tasklist.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.tasklist.presentation.ui.theme.TaskListTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Task1_Coroutines().main()
        //Task2_Coroutines().main()
        //Task3_Coroutines().main()
        //Task4_Coroutines().main()

        //Task_Flow().main()

        enableEdgeToEdge()
        setContent {
            TaskListTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValue ->
                    DestinationsNavHost(
                        modifier = Modifier.fillMaxSize().padding(paddingValue),
                        navGraph = NavGraphs.root
                    )
                }
            }
        }
    }
}
