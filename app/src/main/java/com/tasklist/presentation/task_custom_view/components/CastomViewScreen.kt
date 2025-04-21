package com.tasklist.presentation.task_custom_view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.tasklist.presentation.components.MyView


@Composable
fun CustomViewScreen() {

    Preview()

}

@Composable
@Preview
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Green)
    ) {
        MyView(modifier = Modifier.background(Color.Yellow))
        Text("базовый")

        MyView(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
        )
        Text("на всю ширину")

        Row {
            MyView(
                modifier = Modifier
                    .background(Color.Red)
                    .weight(1f)
            )
            MyView(
                modifier = Modifier
                    .background(Color.Yellow)
                    .weight(1f)
            )
        }
        Text("2 вью на всю ширину экрана")
    }
}