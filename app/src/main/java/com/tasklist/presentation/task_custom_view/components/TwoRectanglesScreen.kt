package com.tasklist.presentation.task_custom_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


@Composable
fun TwoRectanglesScreen() {
    Test()
}

fun Offset.toIntOffset() = IntOffset(x.toInt(), y.toInt())

@Composable
fun Test() {

    var boxOffSet by remember { mutableStateOf(IntOffset.Zero) }

    var baseBoxPosition by remember { mutableStateOf(IntOffset.Zero) }
    var baseBoxSize by remember { mutableStateOf(IntSize.Zero) }

    var parentPosition by remember { mutableStateOf(IntOffset.Zero) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .onGloballyPositioned {
                    parentPosition = it
                        .positionInRoot()
                        .toIntOffset()
                    parentSize = it.size
                }
                .size(200.dp)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .onGloballyPositioned {
                        baseBoxPosition = it.positionInRoot().toIntOffset()
                        baseBoxSize = it.size
                    }
            ) {
                Box(
                    modifier = Modifier
                        .offset { boxOffSet }
                        .pointerInput(Unit) {
                            detectDragGestures { change, dragAmount ->
                                val newOffset = boxOffSet + dragAmount.toIntOffset()
                                val newBoxPosition = baseBoxPosition + newOffset

                                if (
                                    newBoxPosition.x >= parentPosition.x &&
                                    newBoxPosition.x <= parentPosition.x + parentSize.width - baseBoxSize.width &&
                                    newBoxPosition.y >= parentPosition.y &&
                                    newBoxPosition.y <= parentPosition.y + parentSize.height - baseBoxSize.height
                                ) {
                                    boxOffSet = newOffset
                                }
                            }
                        }
                        .size(60.dp)
                        .background(Color.Red)
                ) { }
            }

        }
    }
}

@Preview
@Composable
fun Test2() {

    var parentSize by remember { mutableStateOf(IntSize.Zero) }
    var childSize by remember { mutableStateOf(IntSize.Zero) }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .width(200.dp)
                .height(250.dp)
                .background(Color.Black)
                .onGloballyPositioned { coordinates ->
                    parentSize = coordinates.size
                },
            contentAlignment = Alignment.Center
        ) {

            Layout(
                content = {
                    Box(
                        modifier = Modifier
                            .width(45.dp)
                            .height(60.dp)
                            .background(Color.Red)
                            .onGloballyPositioned { coordinates ->
                                childSize = coordinates.size
                            }
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consume()

                                    val maxOffsetX = (parentSize.width - childSize.width).toFloat()
                                    val maxOffsetY = (parentSize.height - childSize.height).toFloat()

                                    offsetX = (offsetX + dragAmount.x).coerceIn(0f, maxOffsetX)
                                    offsetY = (offsetY + dragAmount.y).coerceIn(0f, maxOffsetY)
                                }
                            }
                    ) { }
                },
                modifier = Modifier
            ) { measurables, constraints ->
                val placeable = measurables.first().measure(constraints)

                layout(constraints.maxWidth, constraints.maxHeight) {
                    placeable.place(offsetX.roundToInt(), offsetY.roundToInt())
                }
            }


        }
    }
}