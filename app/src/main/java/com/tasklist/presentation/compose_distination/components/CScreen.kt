package com.tasklist.presentation.compose_distination.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.tasklist.R
import com.tasklist.presentation.NavGraphs
import com.tasklist.presentation.compose_distination.NestedNavGraph
import com.tasklist.presentation.compose_distination.SharedViewModel

import com.tasklist.presentation.compose_distination.sharedViewModel
import java.util.UUID


@NestedNavGraph
@Destination()
@Composable
fun CScreen(
    navigator: DestinationsNavigator,
    navController: NavController,
    resultNavigator: ResultBackNavigator<String>
    ) {

    val sharedViewModel: SharedViewModel = navController.sharedViewModel()

    UI(navigator = navigator, resultNavigator = resultNavigator)
}

@Composable
private fun UI(
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<String>
) {

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = {
            val newRandomText = UUID.randomUUID().toString().take(4)
            resultNavigator.navigateBack(result = newRandomText)
        }) {
            Text(stringResource(id = R.string.go_to_b_screen))
        }

        Button(onClick = {
            navigator.popBackStack(
                route = NavGraphs.nested.startRoute,
                inclusive = true
            )

        }) {
            Text(stringResource(id = R.string.close_nested_graph))
        }
    }

}
