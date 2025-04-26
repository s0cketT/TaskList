package com.tasklist.presentation.task_custom_view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.tasklist.presentation.ui.theme.CardBackgroundColor
import com.tasklist.presentation.ui.theme.DarkPrimary
import com.tasklist.presentation.ui.theme.DarkText
import com.tasklist.presentation.ui.theme.TextSelectionBackground
import com.tasklist.presentation.ui.theme.TextSelectionHandle

@RootNavGraph
@Destination
@Composable
fun FlowRowAnimateVisibilityScreen() {
    FlowRowExample()
    //ComposableEffectScreen()
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
@Preview
fun FlowRowExample() {

    var visible by remember { mutableStateOf(true) }
    var expanded by remember { mutableStateOf(false) }
    var items by remember { mutableStateOf((1..5).toList()) }

    val animatedSize by animateFloatAsState(
        targetValue = if (expanded) 200f else 100f,
        animationSpec = tween(
            durationMillis = 3000,
            easing = LinearEasing
        ),
        label = ""
    )

    val animatedColor by animateColorAsState(
        targetValue = if (expanded) TextSelectionBackground else TextSelectionHandle,
        animationSpec = tween(
            durationMillis = 3000,
            easing = LinearOutSlowInEasing
        ),
        label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("AnimatedVisibility:", color = DarkText)

        Button(onClick = { visible = !visible }) {
            Text(
                if (visible) "Спрятать" else "Показать",
                color = DarkText
            )
        }

        AnimatedVisibility(
            visible = visible,
            enter = scaleIn(
                animationSpec = tween(durationMillis = 1000),
                initialScale = 0.3f
            ),
            exit = scaleOut(
                animationSpec = tween(durationMillis = 1000),
                targetScale = 0.3f
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(animatedSize.dp)
                    .background(animatedColor)
                    .clickable { expanded = !expanded },
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    "FlowRow с элементами:",
                    color = DarkText
                )

                FlowRow(
                    modifier = Modifier.padding(8.dp),
                    maxLines = 2
                ) {
                    repeat(10) {
                        Box(
                            modifier = Modifier
                                .background(Color.Blue)
                                .padding(8.dp)
                        ) {
                            Text("Item $it", color = DarkText)
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }
            }
        }


        Text("Анимация перестановки:")
        Button(onClick = { items = items.shuffled() }) {
            Text(
                "Перемешать",
                color = DarkText
            )
        }

        LazyColumn(
            modifier = Modifier
                .height(200.dp)
                .background(DarkPrimary),
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(items, key = { _, item -> item }) { _, item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(8.dp)
                        .animateItemPlacement(
                            animationSpec = tween(
                                durationMillis = 3000,
                                easing = FastOutSlowInEasing
                            )
                        ),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBackgroundColor)
                ) {
                    Text(
                        "Item $item",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = DarkText
                    )
                }
            }
        }
    }
}

