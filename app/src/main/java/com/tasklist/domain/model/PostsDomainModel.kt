package com.tasklist.domain.model


data class PostsDomainModel(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

