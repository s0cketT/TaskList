package com.tasklist.presentation.Navigation.components

import androidx.navigation.NavController
import com.tasklist.GsonUtil.toJson
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.presentation.Navigation.Screens

fun NavController.navigateToComments(post: PostsDomainModel) {
    this.currentBackStackEntry?.savedStateHandle?.set("post", post.toJson())
    this.navigate(Screens.Task2Comments.route)
}