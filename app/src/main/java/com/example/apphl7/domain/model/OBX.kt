package com.example.apphl7.domain.model

data class OBX(
    val setId: String?,
    val valueType: String?,
    val observationId: String?,
    val observationSubId: String?,
    val observationValue: String?,
    val units: String?,
    val referenceRange: String?,
    val abnormalFlags: String?,
    val probability: String?,
    val natureOfAbnormalTest: String?,
    val observationResultStatus: String?
)
