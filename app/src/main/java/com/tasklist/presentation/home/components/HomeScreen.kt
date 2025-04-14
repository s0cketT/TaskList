package com.tasklist.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tasklist.R
import com.tasklist.presentation.home.HomeViewModel
import com.tasklist.presentation.ui.theme.CustomOnBackground
import com.tasklist.presentation.ui.theme.DarkBG
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {

    val tasks = viewModel.getTasks()

    Column(
        modifier = Modifier.fillMaxSize()
            .background(DarkBG),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.title),
            fontSize = 26.sp,
            color = CustomOnBackground
            )

        LazyColumn(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            items(tasks) { task ->
                TaskItem(task, navController)
            }
        }
    }

}