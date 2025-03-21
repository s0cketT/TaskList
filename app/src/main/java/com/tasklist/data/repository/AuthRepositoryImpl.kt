package com.tasklist.data.repository

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.auth.InvalidCredentialsException
import com.tasklist.domain.repository.IAuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl: IAuthRepository {

    private val allowedCredentials = listOf(
        "user1" to "password1",
        "admin" to "admin",
        "guest" to "guestPass"
    )

    override suspend fun isValidCredentials(login: String, password: String): Boolean {
        delay(3000)
        return allowedCredentials.contains(login to password)
    }
}
