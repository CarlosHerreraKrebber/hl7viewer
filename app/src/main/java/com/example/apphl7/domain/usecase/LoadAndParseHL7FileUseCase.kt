package com.example.apphl7.domain.usecase

import android.content.Context
import com.example.apphl7.domain.model.HL7Message
import com.example.apphl7.domain.repository.HL7Repository
import javax.inject.Singleton

@Singleton
class LoadAndParseHL7FileUseCase(private val repository: HL7Repository) {
    operator fun invoke(context: Context, fileName: String): HL7Message {
        return repository.loadAndParseFromAssets(context, fileName)
    }
}