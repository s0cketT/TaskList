package com.tasklist.presentation.compose_distination

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@NavGraph
annotation class NestedNavGraph(
    val start: Boolean = false
)