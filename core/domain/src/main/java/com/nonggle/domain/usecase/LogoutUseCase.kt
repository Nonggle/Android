package com.nonggle.domain.usecase

import com.nonggle.domain.repository.LoginRepository
import com.nonggle.model.AppResult
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): AppResult<Unit> {
        return loginRepository.logOut()
    }
}