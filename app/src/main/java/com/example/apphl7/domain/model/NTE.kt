package com.example.apphl7.domain.model

data class NTE(
    val setId: String?,
    val sourceOfComment: String?,
    val comment: String?
) {
    companion object {
        fun fillNTE(segment: HL7Segment): NTE {
            var i = 0
            return NTE(
                setId = segment.fields.getOrNull(i++),
                sourceOfComment = segment.fields.getOrNull(i++),
                comment = segment.fields.getOrNull(i++)
            )
        }
    }
}