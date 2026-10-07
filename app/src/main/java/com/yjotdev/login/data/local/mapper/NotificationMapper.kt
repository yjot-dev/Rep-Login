package com.yjotdev.login.data.local.mapper

import com.yjotdev.login.data.local.entity.NotificationEntity
import com.yjotdev.login.domain.model.NotificationModel

fun NotificationEntity.toDomain() = NotificationModel(
    id = this.id,
    message = this.message,
    date = this.date,
    userId = this.userId
)

fun NotificationModel.toBD() = NotificationEntity(
    id = this.id,
    message = this.message,
    date = this.date,
    userId = this.userId
)