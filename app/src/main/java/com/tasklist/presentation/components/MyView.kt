package com.tasklist.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tasklist.presentation.ui.theme.BorderThickness
import com.tasklist.presentation.ui.theme.ContentPadding

@Composable
fun MyView(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .width(IntrinsicSize.Max)
        .height(IntrinsicSize.Max)
        .border(BorderThickness, Color.White)
        .padding(ContentPadding)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("textFirst")
            Text("textSecond")
        }
    }
}