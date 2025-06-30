package com.example.apphl7.domain.model
// Raw Text HL7 input

data class HL7Segment(
    val name: String, // Segment Name
    val fields: List<String> // Segment content
)
