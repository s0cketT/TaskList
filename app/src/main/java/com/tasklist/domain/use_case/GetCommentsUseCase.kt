package com.tasklist.domain.use_case

import com.tasklist.domain.common.Resource
import com.tasklist.domain.model.CommentsDomainModel
import com.tasklist.domain.model.ExceptionDomainModel
import com.tasklist.domain.repository.ICommentsRepository


class GetCommentsUseCase(private val commentsRepository: ICommentsRepository) {

    suspend operator fun invoke(postId: Int): Resource<List<CommentsDomainModel>, ExceptionDomainModel> {
        return commentsRepository.getComments(postId)
    }
}


