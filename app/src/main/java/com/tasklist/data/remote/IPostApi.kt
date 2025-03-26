package com.tasklist.data.remote

import com.andretietz.retrofit.ResponseCache
import com.tasklist.data.model.PostApiModel
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface IPostApi {
    @GET("posts")
    @ResponseCache(10, TimeUnit.MINUTES)
    suspend fun getPosts(): List<PostApiModel>
}