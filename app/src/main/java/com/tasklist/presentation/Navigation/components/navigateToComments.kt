package com.tasklist.presentation.Navigation.components

import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tasklist.GsonUtil.toJson
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.presentation.destinations.CommentsScreenDestination

fun DestinationsNavigator.navigateToComments(post: PostsDomainModel) {
    val postJson = post.toJson()
    this.navigate(CommentsScreenDestination(postJson = postJson))
}
