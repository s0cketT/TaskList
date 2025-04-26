package com.tasklist.presentation.home.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tasklist.R
import com.tasklist.presentation.SecondActivity
import com.tasklist.presentation.destinations.FlowRowAnimateVisibilityScreenDestination
import com.tasklist.presentation.destinations.PostApiScreenDestination
import com.tasklist.presentation.destinations.Task1ScreenDestination
import com.tasklist.presentation.destinations.TaskPagesScreenDestination
import com.tasklist.presentation.destinations.TwoRectanglesScreenDestination
import com.tasklist.presentation.home.HomeEvent
import com.tasklist.presentation.home.HomeIntent
import com.tasklist.presentation.home.HomeViewModel
import com.tasklist.presentation.home.TaskScreen
import com.tasklist.presentation.ui.theme.CustomOnBackground
import com.tasklist.presentation.ui.theme.DarkBG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel
import kotlin.enums.EnumEntries
import kotlin.enums.enumEntries

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
) {

    val homeViewModel: HomeViewModel = koinViewModel()
    val event: Flow<HomeEvent> by remember { mutableStateOf(homeViewModel.event) }
    val intent by remember { mutableStateOf(homeViewModel::processIntent) }

    val context = LocalContext.current

    val allTaskScreens = TaskScreen.entries

    LaunchedEffect(Unit) {
        event.filterIsInstance<HomeEvent.Navigate>().collect {event ->
            when(event.destination) {
                TaskScreen.Task1 -> navigator.navigate(Task1ScreenDestination)
                TaskScreen.Task2PostApi -> navigator.navigate(PostApiScreenDestination)
                TaskScreen.TaskPages -> navigator.navigate(TaskPagesScreenDestination)
                TaskScreen.TwoRectangles -> navigator.navigate(TwoRectanglesScreenDestination)
                TaskScreen.FlowRowAnimateVisibility -> navigator.navigate(FlowRowAnimateVisibilityScreenDestination)
                TaskScreen.ComposeDestination -> context.startActivity(Intent(context, SecondActivity::class.java))
            }
        }
    }

    UI(allTaskScreens = allTaskScreens, intent = intent)
}

@Preview
@Composable
private fun UI(
    allTaskScreens: EnumEntries<TaskScreen> = enumEntries<TaskScreen>(),
    intent: (HomeIntent) -> Unit = {}
) {
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
            items(allTaskScreens) { task ->
                TaskItem(taskScreen = task, intent = intent)
            }
        }
    }
}

