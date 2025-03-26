package com.tasklist.presentation.task_2.post_api.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tasklist.R
import com.tasklist.presentation.Navigation.components.navigateToComments
import com.tasklist.presentation.components.CustomLoader
import com.tasklist.presentation.components.CustomTextField
import com.tasklist.presentation.components.PostItem
import com.tasklist.presentation.task_2.post_api.PostApiViewModel
import com.tasklist.presentation.task_2.post_api.model.PostIntent
import com.tasklist.presentation.task_2.post_api.model.PostState
import com.tasklist.presentation.task_2.post_api.model.Task2Event
import com.tasklist.presentation.ui.theme.CardBackgroundColor
import com.tasklist.presentation.ui.theme.DarkBG
import com.tasklist.presentation.ui.theme.DarkError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.androidx.compose.koinViewModel

@Composable
fun PostApiScreen(
    navController: NavController
) {

    val postApiViewModel = koinViewModel<PostApiViewModel>()
    val state by postApiViewModel.state.collectAsStateWithLifecycle()
    val intent by remember { mutableStateOf(postApiViewModel::processIntent) }
    val event: Flow<Task2Event> by remember { mutableStateOf(postApiViewModel.event) }

    LaunchedEffect(Unit) {
        event.filterIsInstance<Task2Event.NavigateToCommentsScreen>().collect { event ->
            navController.navigateToComments(event.post)
        }
    }

    PostApiUI(state, intent)
}


@Composable
@Preview
private fun PostApiUI(
    state: PostState = PostState(),
    intent: (PostIntent) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBG),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            CustomTextField(
                text = state.textField,
                onTextChange = { intent(PostIntent.EnterTextField(it)) },
                hint = stringResource(id = R.string.basic_text_field_hint),
                modifier = Modifier.padding(16.dp)
            )

        }

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomLoader()
                    }
                }

                state.posts.isNotEmpty() -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(state.posts, key = { index, post -> post.id }) { index, post ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        intent(PostIntent.NavigateToCommentsScreen(post))
                                               },
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(containerColor = CardBackgroundColor)
                            ) {
                                PostItem(post)
                            }
                        }
                    }
                }

                state.error != null -> {
                    Text(
                        text = stringResource(id = state.error),
                        color = DarkError,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    Text(
                        text = stringResource(id = R.string.error_api),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}




