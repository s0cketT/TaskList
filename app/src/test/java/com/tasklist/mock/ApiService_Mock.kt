package com.tasklist.mock

import com.tasklist.data.model.CommentsApiModel
import com.tasklist.data.model.PostApiModel
import com.tasklist.data.remote.ICommentsApi
import com.tasklist.data.remote.IPostApi
import org.koin.dsl.module

class ApiService_Mock {

    companion object {
        val initPosts = (0..10).map {
            PostApiModel(
                userId = it.toString(),
                id = it.toString(),
                title = it.toString(),
                body = it.toString(),
            )
        }

        val initComments = (0..4).map {
            CommentsApiModel(
                id = it.toString(),
                postId = it.toString(),
                name = it.toString(),
                email = it.toString(),
                body = it.toString()
            )
        }
    }


    fun mModule(
        getPost: () -> List<PostApiModel> = { initPosts },
        getComments: () -> List<CommentsApiModel> = { initComments }
    ) = module {
        single<IPostApi> {
            object : IPostApi {
                override suspend fun getPosts() = getPost.invoke()
            }
        }

        single<ICommentsApi> {
            object : ICommentsApi {
                override suspend fun getComments(postId: Int): List<CommentsApiModel>  = getComments.invoke()
            }
        }
    }

}