package com.example.apphl7.domain.model


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
) {
    companion object {
        fun parseMSH(segment: HL7Segment): MSH {
            return MSH(
                fieldSeparator = '|',
                encodingChars = segment.fields.getOrElse(0) { "^~&" },
                sendingApp = segment.fields.getOrNull(1),
                sendingFacility = segment.fields.getOrNull(2),
                receivingApp = segment.fields.getOrNull(3),
                receivingFacility = segment.fields.getOrNull(4),
                dateTimeOfMessage = segment.fields.getOrNull(5),
                security = segment.fields.getOrNull(6),
                messageType = segment.fields.getOrNull(7),
                messageControlId = segment.fields.getOrNull(8),
                processingId = segment.fields.getOrNull(9),
                versionId = segment.fields.getOrNull(10),
                sequenceNumber = segment.fields.getOrNull(11),
                continuationPointer = segment.fields.getOrNull(12),
                acceptAcknowledgmentType = segment.fields.getOrNull(13),
                applicationAcknowledgmentType = segment.fields.getOrNull(14),
                countryCode = segment.fields.getOrNull(15)
            )
        }
    }
}