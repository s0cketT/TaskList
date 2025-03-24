package com.tasklist.domain.repository


interface IAuthRepository {
    suspend fun isValidCredentials(login: String, password: String): Boolean
}
