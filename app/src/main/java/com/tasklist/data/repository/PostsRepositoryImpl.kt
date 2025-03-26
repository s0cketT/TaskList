package com.tasklist.data.repository

import com.tasklist.data.extensions.mapToExceptionDomainModel
import com.tasklist.data.model.PostApiModel.Companion.toDomainModelList
import com.tasklist.data.remote.IPostApi
import com.tasklist.domain.common.Resource
import com.tasklist.domain.model.ExceptionDomainModel
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.domain.repository.IPostsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PostsRepositoryImpl(private val postApi: IPostApi) : IPostsRepository {
    override suspend fun getPosts(): Resource<List<PostsDomainModel>, ExceptionDomainModel> {
        return withContext(Dispatchers.IO) {
            runCatching {
                val result = postApi.getPosts().toDomainModelList()
                Resource.Success<List<PostsDomainModel>, ExceptionDomainModel>(result)
            }.getOrElse {
                Resource.Error(it.mapToExceptionDomainModel())
            }
        }
    }
}

