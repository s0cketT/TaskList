package com.tasklist.data.model

import android.util.Log
import com.tasklist.domain.model.PostsDomainModel


data class PostApiModel(
    val body: String?,
    val id: String?,
    val title: String?,
    val userId: String?
) {
    fun toDomainModel(): PostsDomainModel? = runCatching {
        PostsDomainModel(
            userId = this.userId?.toInt()!!,
            id = this.id?.toInt()!!,
            title = this.title!!,
            body = this.body!!
        )
    }.getOrElse {
        Log.e("!!!", it.stackTraceToString())
        null
    }

    companion object {
        fun List<PostApiModel>.toDomainModelList(): List<PostsDomainModel> {
            val mapResult = this.mapNotNull { it.toDomainModel() }
            val invalidCount = this.size - mapResult.size

            if (invalidCount != 0) {
                Log.e("!!!", "Количество битых данных $invalidCount")
            }

            return mapResult
        }
    }


}