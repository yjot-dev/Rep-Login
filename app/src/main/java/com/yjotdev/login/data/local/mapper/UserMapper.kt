package com.yjotdev.login.data.local.mapper

import com.yjotdev.login.data.local.entity.UserEntity
import com.yjotdev.login.domain.model.UserModel

fun UserEntity.toDomain() = UserModel(
    id = this.id,
    name = this.name,
    email = this.email,
    password = this.password,
    isInvited = this.isInvited,
    isInWhiteList = this.isInWhiteList
)

fun UserModel.toBD() = UserEntity(
    id = this.id,
    name = this.name,
    email = this.email,
    password = this.password,
    isInvited = this.isInvited,
    isInWhiteList = this.isInWhiteList
)