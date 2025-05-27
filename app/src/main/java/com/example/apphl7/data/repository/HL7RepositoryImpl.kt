package com.example.apphl7.data.repository

import com.example.apphl7.data.parser.HL7Parser
import com.example.apphl7.domain.model.HL7Message
import com.example.apphl7.domain.repository.HL7Repository

class HL7RepositoryImpl: HL7Repository {

    override fun parseHL7Content(content: String): HL7Message {
        return HL7Parser.parse(content)
    }

}