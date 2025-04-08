package com.tasklist.presentation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.presentation.ui.theme.CardContentTextColor
import com.tasklist.presentation.ui.theme.CardTextColor
import com.tasklist.presentation.ui.theme.HeartGradientColors

@Composable
fun PostItem(
    post: PostsDomainModel = PostsDomainModel(0, 0, "", "", false),
    onFavoriteClick: () -> Unit = {},
    isFavorites: Boolean = false
) {


    val infiniteTransition = rememberInfiniteTransition(label = "")

    val animatedScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val animatedColor by infiniteTransition.animateColor(
        initialValue = HeartGradientColors.first(),
        targetValue = HeartGradientColors.last(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(500)
        ), label = ""
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = post.title,
                color = CardTextColor,
                modifier = Modifier.weight(1f),
            )

            IconButton(onClick = { onFavoriteClick() }) {
                Icon(
                    imageVector = if (isFavorites) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "",
                    tint = if (isFavorites) animatedColor else Color.Gray,
                    modifier = Modifier.scale(if (isFavorites) animatedScale else 1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = post.body, color = CardContentTextColor)

    }
}
