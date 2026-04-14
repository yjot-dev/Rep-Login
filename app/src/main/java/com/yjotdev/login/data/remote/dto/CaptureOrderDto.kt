package com.yjotdev.login.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CaptureOrderRequestDto(
    @SerializedName("id") val id: String = ""
)

data class CaptureOrderResponseDto(
    @SerializedName("id") val id: String = "",
    @SerializedName("status") val status: String = "",
    @SerializedName("purchase_units") val purchaseUnit: List<PurchaseUnitDto> = listOf(),
    @SerializedName("payer") val payer: PayerDto = PayerDto("")
)

data class PurchaseUnitDto(
    @SerializedName("amount") val amount: AmountDto
)

data class AmountDto(
    @SerializedName("currency_code") val currencyCode: String,
    @SerializedName("value") val value: String
)

data class PayerDto(
    @SerializedName("email_address") val emailAddress: String
)