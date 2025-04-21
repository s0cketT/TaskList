package com.tasklist.presentation.task_custom_view.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ComposableEffectScreen() {
    var key by remember { mutableStateOf(0) }

    LaunchedEffect(key) {
        Log.e("!!!", "LaunchEffect - $key")
    }

    SideEffect {
        Log.e("!!!", "SideEffect - $key")
    }

    DisposableEffect(key) {
        Log.e("!!!", "DisposableEffect - $key")

        onDispose {
            Log.e("!!!", "OnDispose - $key")
        }
    }


    Column(modifier = Modifier.fillMaxSize(),) {
        Button(onClick = { key++ }) {
        }
        Text(
            text = "Elapsed Time",
            modifier = Modifier.padding(16.dp),
            fontSize = 24.sp
        )
    }
}
