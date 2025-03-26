package com.tasklist.domain.use_case

import com.tasklist.domain.common.Resource
import com.tasklist.domain.model.ExceptionDomainModel
import com.tasklist.domain.model.PostsDomainModel
import com.tasklist.domain.repository.IPostsRepository

class GetPostsUseCase(private val postRepository: IPostsRepository) {

    suspend operator fun invoke(): Resource<List<PostsDomainModel>, ExceptionDomainModel> {
        return postRepository.getPosts()
    }
}
