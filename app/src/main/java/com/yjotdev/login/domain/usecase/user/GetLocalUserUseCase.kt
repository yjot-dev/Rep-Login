package com.yjotdev.login.domain.usecase.user

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.repository.UserDaoRepository

class GetLocalUserUseCase @Inject constructor(
    private val userDaoRepository: UserDaoRepository
) {
    operator fun invoke(): Flow<UserModel> {
        return userDaoRepository.getLocalUser()
    }
}