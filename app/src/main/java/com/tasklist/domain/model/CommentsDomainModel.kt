package com.tasklist.domain.model

data class CommentsDomainModel(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)
