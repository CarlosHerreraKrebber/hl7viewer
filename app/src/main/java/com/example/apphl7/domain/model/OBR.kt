package com.example.apphl7.domain.model

data class OBR (
    val setId: String?,
    val placerOrderNumber: String?,
    val fillerOrderNumber: String?,
    val universalServiceId: String?,
    val priority: String?,
    val requestedDateTime: String?,
    val observationDateTime: String?,
    val observationEndDateTime: String?,
    val collectionVolume: String?,
    val collectorIdentifier: String?
)