package com.tasklist.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.presentation.ui.theme.CardContentTextColor
import com.tasklist.presentation.ui.theme.CardTextColor

@Composable
fun PostItem(
    post: PostsDomainModel
) {

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = post.title, color = CardTextColor)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = post.body, color = CardContentTextColor)

    }
}