package com.tasklist.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.tasklist.presentation.destinations.Task1ScreenDestination
import com.tasklist.presentation.home.HomeIntent
import com.tasklist.presentation.home.TaskScreen

@Composable
fun TaskItem(
    taskScreen: TaskScreen,
    intent: (HomeIntent) -> Unit = {}
) {

    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
        ) {
        Task1ScreenDestination
        Button(onClick = {
            intent(HomeIntent.Navigate(taskScreen))
        },
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.18f),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
            Text(text = taskScreen.displayName,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onPrimary
                )
        }
    }

}
