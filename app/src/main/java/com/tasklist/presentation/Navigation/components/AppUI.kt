package com.tasklist.presentation.Navigation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tasklist.GsonUtil.fromJson
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.presentation.Navigation.Screens
import com.tasklist.presentation.Task_1.components.Task1Screen
import com.tasklist.presentation.auto_migration_room.components.AutoMigrationRoomScreen
import com.tasklist.presentation.home.components.HomeScreen
import com.tasklist.presentation.task_2.comments.components.CommentsScreen
import com.tasklist.presentation.task_2.post_api.components.PostApiScreen

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

        composable(Screens.Task2PostApi.route) {
            PostApiScreen(navController)
        }

        composable(Screens.Task2Comments.route) {
            val postJson = navController.previousBackStackEntry?.savedStateHandle?.get<String>("post")
            val post = postJson?.fromJson<PostsDomainModel>()

            post?.let { CommentsScreen(it) }
        }

        composable(Screens.TaskАutoMigrationRoom.route) {
            AutoMigrationRoomScreen()
        }
    }

}

