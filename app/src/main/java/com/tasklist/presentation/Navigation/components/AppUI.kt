package com.tasklist.presentation.Navigation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tasklist.presentation.Home.components.HomeScreen
import com.tasklist.presentation.Navigation.Screens
import com.tasklist.presentation.Task_1.components.Task1Screen

@Composable
fun AppUI() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
    ) {
        composable(Screens.Home.route) {
            HomeScreen(navController)
        }
        composable(Screens.Task1.route) {
            Task1Screen(navController)
        }

        composable(Screens.Task1Success.route) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
                ) {
                Text("Success")
            }

        }

    }

}