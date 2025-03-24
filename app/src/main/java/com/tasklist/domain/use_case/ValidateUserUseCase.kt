package com.tasklist.domain.use_case

import com.tasklist.domain.repository.IAuthRepository

class ValidateUserUseCase(private val authRepository: IAuthRepository) {
    suspend operator fun invoke(login: String, password: String): Boolean {
        return authRepository.isValidCredentials(login, password)
    }
}