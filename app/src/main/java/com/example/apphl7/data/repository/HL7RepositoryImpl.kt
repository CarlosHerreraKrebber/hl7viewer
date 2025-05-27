package com.example.apphl7.data.repository

import android.content.Context
import com.example.apphl7.data.dataLoader.HL7FileLoader
import com.example.apphl7.data.parser.HL7Parser
import com.example.apphl7.domain.model.HL7Message
import com.example.apphl7.domain.repository.HL7Repository

class HL7RepositoryImpl(
    private val loader: HL7FileLoader
    ): HL7Repository {
    override fun loadAndParseFromAssets(context: Context, fileName: String): HL7Message {
        val content:String = loader.loadHL7ContentFromAssets(context, fileName)
        return HL7Parser.parse(content)
    }
}