package com.tasklist.data.model

import android.util.Log
import com.tasklist.domain.model.CommentsDomainModel


data class CommentsApiModel(
    val id: String?,
    val postId: String?,
    val name: String?,
    val email: String?,
    val body: String?
) {
    fun toDomainModel(): CommentsDomainModel? {
        return kotlin.runCatching {
            CommentsDomainModel(
                id = id?.toInt()!!,
                postId = postId?.toInt()!!,
                name = name!!,
                email = email!!,
                body = body!!
            )
        }.getOrElse {
            null
        }
    }

    companion object {
        fun List<CommentsApiModel>.toDomainModelList(): List<CommentsDomainModel> {
            val mapResult = this.mapNotNull { it.toDomainModel() }
            val invalidCount = this.size - mapResult.size

            if (invalidCount != 0) {
                Log.e("!!!", "Количество битых данных $invalidCount")
            }

            return mapResult
        }
    }
}
