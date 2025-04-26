package com.tasklist.presentation.compose_distination.components

import android.util.Log
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
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import com.tasklist.R
import com.tasklist.presentation.compose_distination.NestedNavGraph
import com.tasklist.presentation.compose_distination.SharedViewModel
import com.tasklist.presentation.compose_distination.sharedViewModel
import com.tasklist.presentation.destinations.CScreenDestination

@NestedNavGraph(start = true)
@Destination
@Composable
fun BScreen(
    navigator: DestinationsNavigator,
    navController: NavController,
    resultRecipient: ResultRecipient<CScreenDestination, String>
) {

    resultRecipient.onNavResult { result ->
        when(result) {
            NavResult.Canceled -> Log.e("!!!", "canceled - $result")
            is NavResult.Value -> Log.e("!!!", "value - ${result.value}")
        }
    }


    val sharedViewModel: SharedViewModel = navController.sharedViewModel()

    UI(navigator = navigator)
}

@Composable
private fun UI(navigator: DestinationsNavigator) {

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = {
            navigator.navigate(CScreenDestination)
        }) {
            Text(stringResource(id = R.string.go_to_c_screen))
        }
    }

}
