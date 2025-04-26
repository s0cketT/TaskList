package com.tasklist.presentation.compose_distination.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tasklist.R
import com.tasklist.presentation.destinations.BScreenDestination
import com.tasklist.presentation.ui.theme.DarkBG



@RootNavGraph
@Destination
@Composable
fun AScreen(navigator: DestinationsNavigator) {

    UI(navigator = navigator)
}

@Composable
private fun UI(navigator: DestinationsNavigator) {

    Box(
        Modifier.fillMaxSize().background(DarkBG),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            navigator.navigate(BScreenDestination)

        }) {
            Text(stringResource(id = R.string.go_to_b_screen))
        }
    }

}
