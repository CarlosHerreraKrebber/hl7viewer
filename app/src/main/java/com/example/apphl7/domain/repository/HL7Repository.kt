package com.example.apphl7.domain.repository

import com.example.apphl7.domain.model.HL7Message


interface HL7Repository {
    fun parseHL7Content(content: String): HL7Message
}