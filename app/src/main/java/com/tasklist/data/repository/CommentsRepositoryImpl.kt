package com.tasklist.data.repository


import com.tasklist.data.extensions.mapToExceptionDomainModel
import com.tasklist.data.model.CommentsApiModel.Companion.toDomainModelList
import com.tasklist.data.remote.ICommentsApi
import com.tasklist.domain.common.Resource
import com.tasklist.domain.model.CommentsDomainModel
import com.tasklist.domain.model.ExceptionDomainModel
import com.tasklist.domain.repository.ICommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentsRepositoryImpl(private val postByIdApi: ICommentsApi) : ICommentsRepository {
    override suspend fun getComments(postId: Int): Resource<List<CommentsDomainModel>, ExceptionDomainModel> {
         return withContext(Dispatchers.IO) {
            runCatching {
                val result = postByIdApi.getComments(postId).toDomainModelList()
                Resource.Success<List<CommentsDomainModel>, ExceptionDomainModel>(result)
            }.getOrElse {
                Resource.Error(it.mapToExceptionDomainModel())
            }
        }
    }
}

