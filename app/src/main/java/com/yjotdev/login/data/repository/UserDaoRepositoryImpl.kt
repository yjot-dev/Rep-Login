package com.yjotdev.login.data.repository

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.yjotdev.login.data.local.dao.UserDao
import com.yjotdev.login.data.local.mapper.toBD
import com.yjotdev.login.data.local.mapper.toDomain
import com.yjotdev.login.domain.model.UserModel
import com.yjotdev.login.domain.repository.UserDaoRepository

@Singleton
class UserDaoRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): UserDaoRepository {

    override suspend fun updateLocalUser(user: UserModel) {
        return userDao.updateLocalUser(user.toBD())
    }

    override suspend fun insertLocalUser(user: UserModel) {
        return userDao.insertLocalUser(user.toBD())
    }

    override suspend fun deleteLocalUser(user: UserModel) {
        return userDao.deleteLocalUser(user.toBD())
    }

    override fun getLocalUser(): Flow<UserModel> {
        return userDao.getLocalUser().map { it?.toDomain() ?: UserModel() }
    }
}