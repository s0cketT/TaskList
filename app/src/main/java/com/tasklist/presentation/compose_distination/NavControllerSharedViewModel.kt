package com.tasklist.presentation.compose_distination

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.ParametersDefinition

@Composable
inline fun <reified T : ViewModel> NavController.sharedViewModel(
    noinline parameters: ParametersDefinition? = null
): T {
    val navGraphRoute = currentBackStackEntry?.destination?.parent?.route
        ?: return koinViewModel(parameters = parameters)
    val parentEntry = remember(currentBackStackEntry) {
        getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(viewModelStoreOwner = parentEntry, parameters = parameters)
}