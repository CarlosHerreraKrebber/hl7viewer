package com.example.apphl7.domain.repository

import android.content.Context
import com.example.apphl7.domain.model.HL7Message


interface HL7Repository {
    fun loadAndParseFromAssets(context: Context, fileName: String): HL7Message
}