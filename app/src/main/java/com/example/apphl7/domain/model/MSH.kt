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
    val countryCode: String?,
    val charSet: String?,
    val pincipalLanguageMessage: String?
) {
    companion object {
        const val segID = "MSH"

        fun fromSegment(segment: HL7Segment): MSH {
            val f = segment.fields
            var i = 0 // skip MSH segment name
            fun next() = f.getOrNull(i++)
            return MSH(
                fieldSeparator = '|',
                encodingChars = next(),
                sendingApp = next(),
                sendingFacility = next(),
                receivingApp = next(),
                receivingFacility = next(),
                dateTimeOfMessage = next(),
                security = next(),
                messageType = next(),
                messageControlId = next(),
                processingId = next(),
                versionId = next(),
                sequenceNumber = next(),
                continuationPointer = next(),
                acceptAcknowledgmentType = next(),
                applicationAcknowledgmentType = next(),
                countryCode = next(),
                charSet = next(),
                pincipalLanguageMessage = next()
            )
        }
    }
}