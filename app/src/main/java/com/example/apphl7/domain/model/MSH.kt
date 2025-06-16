package com.example.apphl7.domain.model

// to add all the diff segs in ther own classes

data class MSH(
    val fieldSeparator: Char?,
    val encodingChars: String?,
    val sendingApp: String?,
    val sendingFacility: String?,
    val receivingApp: String?,
    val receivingFacility: String?,
    val dateTimeOfMessage: String?,
    val security: String?,
    val messageType: String?,
    val messageControlId: String?,
    val processingId: String?,
    val versionId: String?,
    val sequenceNumber: String?,
    val continuationPointer: String?,
    val acceptAcknowledgmentType: String?,
    val applicationAcknowledgmentType: String?,
    val countryCode: String?
)