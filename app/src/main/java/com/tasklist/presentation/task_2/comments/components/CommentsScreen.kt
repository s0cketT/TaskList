package com.tasklist.presentation.task_2.comments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tasklist.R
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.presentation.components.CustomLoader
import com.tasklist.presentation.components.PostItem
import com.tasklist.presentation.task_2.comments.CommentsIntent
import com.tasklist.presentation.task_2.comments.CommentsState
import com.tasklist.presentation.task_2.comments.CommentsViewModel
import com.tasklist.presentation.ui.theme.CardBackgroundColor
import com.tasklist.presentation.ui.theme.CardContentTextColor
import com.tasklist.presentation.ui.theme.CardTextColor
import com.tasklist.presentation.ui.theme.DarkBG
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CommentsScreen(
    post: PostsDomainModel
) {
    val commentsViewModel: CommentsViewModel = koinViewModel(parameters = { parametersOf(post) })
    val state by commentsViewModel.state.collectAsStateWithLifecycle()
    val intent by remember { mutableStateOf(commentsViewModel::processIntent) }

    UI(state = state, intent = intent)
}

@Composable
@Preview
private fun UI(
    state: CommentsState = CommentsState(currentPost = PostsDomainModel(0, 1, "", "", false)),
    intent: (CommentsIntent) -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkBG)
        .systemBarsPadding()
        .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.post_result),
            color = Color.White,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        PostItem(
            post = state.currentPost,
            onFavoriteClick = { intent((CommentsIntent.IsFavorites(state.currentPost))) },
            isFavorites = state.currentPost.isFavorite)

        Spacer(modifier = Modifier.height(16.dp))

        when {
            state.isLoading -> {
                CustomLoader()
            }

            state.error != null -> {
                Text(text = "Ошибка: ${state.error}", color = Color.Red)
            }

            state.comments.isNotEmpty() -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(state.comments, key = { index, comment -> comment.id }) { index, comment ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = CardBackgroundColor)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = comment.name, color = CardTextColor)
                                Spacer(modifier = Modifier.height(8.dp))
                                comment.body?.let { Text(text = it, color = CardContentTextColor) }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = comment.email, color = CardContentTextColor)

                            }
                        }
                    }
                }
            }

            else -> {
                Text(text = stringResource(id = R.string.comment_is_not_find))
            }
        }
    }
}