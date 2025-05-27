package com.example.apphl7.domain.model

data class HL7Segment(
    val name: String,
    val fields: List<String>
)

data class HL7Message(
    val segments: List<HL7Segment>
)