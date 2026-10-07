package com.yjotdev.login.domain.usecase.user

import javax.inject.Inject
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.repository.UserDaoRepository

class DeleteLocalUserUseCase @Inject constructor(
    private val userDaoRepository: UserDaoRepository
) {
    suspend operator fun invoke(user: UserModel) {
        return userDaoRepository.deleteLocalUser(user)
    }
}