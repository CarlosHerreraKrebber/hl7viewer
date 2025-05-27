package com.example.apphl7.domain.usecase

import com.example.apphl7.domain.model.HL7Message
import com.example.apphl7.domain.repository.HL7Repository

class ParseHL7FileUseCase(private val repository: HL7Repository) {
    operator fun invoke(content: String): HL7Message {
        return repository.parseHL7Content(content)
    }
}