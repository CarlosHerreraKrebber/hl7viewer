package com.example.apphl7.domain.model
// common order Seg
data class ORC(
    val orderControl: String?,
    val placerOrderNumber: String?,
    val fillerOrderNumber: String?,
    val placerGroupNumber: String?,
    val orderStatus: String?,
    val responseFlag: String?,
    val quantityTiming: String?,
    val parent: String?,
    val transactionDateTime: String?,
    val enteredBy: String?,
    val verifiedBy: String?,
    val orderingProvider: String?,
    val entererLocation: String?,
    val callBackPhoneNumber: String?,
    val orderEffectiveDateTime: String?
)