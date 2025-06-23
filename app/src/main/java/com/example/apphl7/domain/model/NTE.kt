package com.example.apphl7.domain.model

data class NTE(
    val setId: String?,
    val sourceOfComment: String?,
    val comment: String?
) {
    companion object {
        const val segID = "NTE"

        fun fromSegment(segment: HL7Segment): NTE {
            val f = segment.fields
            var i = 0
            fun next() = f.getOrNull(i++)
            return NTE(
                setId = next(),
                sourceOfComment = next(),
                comment = next()
            )
        }
    }
}