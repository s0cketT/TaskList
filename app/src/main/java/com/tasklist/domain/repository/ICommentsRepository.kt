package com.tasklist.domain.repository

import com.tasklist.domain.common.Resource
import com.tasklist.domain.model.CommentsDomainModel
import com.tasklist.domain.model.ExceptionDomainModel

interface ICommentsRepository {
    suspend fun getComments(postId: Int): Resource<List<CommentsDomainModel>, ExceptionDomainModel>
}