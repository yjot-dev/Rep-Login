package com.yjotdev.login.domain.model

data class CaptureOrderRequestModel(
    val id: String = ""
)

data class CaptureOrderResultModel(
    val id: String = "",
    val status: String = "",
    val purchaseUnits: List<PurchaseUnitModel> = listOf(),
    val payer: PayerModel = PayerModel("")
)

data class PurchaseUnitModel(
    val amount: AmountModel
)

data class AmountModel(
    val currencyCode: String,
    val value: String
)

data class PayerModel(
    val emailAddress: String
)