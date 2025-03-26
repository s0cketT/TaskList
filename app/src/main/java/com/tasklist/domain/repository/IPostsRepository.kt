package com.tasklist.domain.repository


import com.tasklist.domain.common.Resource
import com.tasklist.domain.model.ExceptionDomainModel
import com.tasklist.domain.model.PostsDomainModel

interface IPostsRepository {

    suspend fun getPosts(): Resource<List<PostsDomainModel>, ExceptionDomainModel>

}