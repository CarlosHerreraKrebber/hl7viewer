package com.example.apphl7.domain.model

data class HL7Message(
    val segments: List<HL7Segment>
)