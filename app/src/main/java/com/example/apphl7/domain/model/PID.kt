package com.example.apphl7.domain.model

data class PID(
    val setId: String,
    val patientId: String,
    val patientIdentifierList: String,
    val alternatePatientId: String,
    val patientName: String,
    val motherMaidenName: String,
    val birthDate: String,
    val sex: String,
    val patientAlias: String,
    val race: String,
    val address: String
)
{
    companion object {
        fun parsePID(segment: HL7Segment): PID {
            return PID(
                setId = segment.fields.get(0),
                patientId = segment.fields.get(2),
                patientIdentifierList = segment.fields.get(3),
                alternatePatientId = segment.fields.get(4),
                patientName = segment.fields.get(5),
                motherMaidenName = segment.fields.get(6),
                birthDate = segment.fields.get(7),
                sex = segment.fields.get(8),
                patientAlias = segment.fields.get(9),
                race = segment.fields.get(10),
                address = segment.fields.get(11)
            )
        }
    }
}