package com.tasklist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.tasklist.presentation.destinations.AScreenDestination
import com.tasklist.presentation.ui.theme.TaskListTheme

class SecondActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TaskListTheme {
                val navHostEngine = rememberNavHostEngine(
                    rootDefaultAnimations = RootNavGraphDefaultAnimations(
                        enterTransition = { fadeIn(animationSpec = tween(700)) },
                        exitTransition = {
                            fadeOut(animationSpec = tween(700)) }
                    ),
                    defaultAnimationsForNestedNavGraph = mapOf(
                        NavGraphs.nested to NestedNavGraphDefaultAnimations(
                            enterTransition = {
                                scaleIn(
                                    initialScale = 0.3f,
                                    animationSpec = tween(500)) },
                            exitTransition = {
                                scaleOut(
                                    targetScale = 0.3f,
                                    animationSpec = tween(500)) }
                        )
                    )
                )
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValue ->
                    DestinationsNavHost(
                        engine = navHostEngine,
                        navGraph = NavGraphs.root,
                        startRoute = AScreenDestination,
                        modifier = Modifier.fillMaxSize().padding(paddingValue),
                    )
                }
            }
        }
    }
}








