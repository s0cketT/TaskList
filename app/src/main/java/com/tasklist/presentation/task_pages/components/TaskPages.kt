package com.tasklist.presentation.task_pages.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasklist.presentation.components.CustomLoader
import com.tasklist.presentation.task_pages.TaskPagesIntent
import com.tasklist.presentation.task_pages.TaskPagesState
import com.tasklist.presentation.task_pages.TaskPagesViewModel
import com.tasklist.presentation.ui.theme.CardBackgroundColor
import org.koin.androidx.compose.koinViewModel


@Composable
fun TaskPages() {

    val taskPagesViewModel = koinViewModel<TaskPagesViewModel>()
    val state by taskPagesViewModel.state.collectAsStateWithLifecycle()
    val intent by remember { mutableStateOf(taskPagesViewModel::processIntent) }

    UI(state = state, intent = intent)

}

@Composable
@Preview
private fun UI(
    state: TaskPagesState = TaskPagesState(),
    intent: (TaskPagesIntent) -> Unit = {}
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        when {

            state.data.isNotEmpty() -> {
                LazyColumn(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    itemsIndexed(state.data) { index, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 50.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
                        ) {
                            Text(
                                text = "Элемент: $item",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }

                        if (index >= state.data.size - state.pageSize) {
                            intent(TaskPagesIntent.LoadingNextPages)
                        }
                    }

                    if (state.isAppending) {
                        item {
                            CustomLoader()
                        }
                    }

                }
            }

            else -> {
                Text(
                    text = "Нет данных",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }


}