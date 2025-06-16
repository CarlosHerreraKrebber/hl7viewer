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