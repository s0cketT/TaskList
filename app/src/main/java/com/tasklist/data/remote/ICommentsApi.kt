package com.tasklist.data.remote

import com.andretietz.retrofit.ResponseCache
import com.tasklist.data.model.CommentsApiModel
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ICommentsApi {
    @GET("comments")
    @ResponseCache(10, TimeUnit.MINUTES)
    suspend fun getComments(@Query("postId") postId: Int): List<CommentsApiModel>
}


