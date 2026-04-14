package com.yjotdev.login.data.remote.mapper

import com.yjotdev.login.data.remote.dto.CaptureOrderRequestDto
import com.yjotdev.login.data.remote.dto.CaptureOrderResponseDto
import com.yjotdev.login.data.remote.dto.PurchaseUnitDto
import com.yjotdev.login.data.remote.dto.AmountDto
import com.yjotdev.login.data.remote.dto.PayerDto
import com.yjotdev.login.domain.model.CaptureOrderRequestModel
import com.yjotdev.login.domain.model.CaptureOrderResultModel
import com.yjotdev.login.domain.model.PurchaseUnitModel
import com.yjotdev.login.domain.model.AmountModel
import com.yjotdev.login.domain.model.PayerModel

fun CaptureOrderRequestModel.toDto() = CaptureOrderRequestDto(
    id = this.id
)

fun CaptureOrderResponseDto.toDomain() = CaptureOrderResultModel(
    id = this.id,
    status = this.status,
    purchaseUnits = this.purchaseUnit.map { it.toDomain() },
    payer = this.payer.toDomain()
)

fun PurchaseUnitDto.toDomain() = PurchaseUnitModel(
    amount = this.amount.toDomain()
)

fun AmountDto.toDomain() = AmountModel(
    currencyCode = this.currencyCode,
    value = this.value
)

fun PayerDto.toDomain() = PayerModel(
    emailAddress = this.emailAddress
)