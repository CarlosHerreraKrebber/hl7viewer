package com.example.apphl7.data.dataLoader

import android.content.Context
import javax.inject.Inject

class HL7FileLoader @Inject constructor() {
    fun loadHL7ContentFromAssets(context: Context, fileName: String): String {
        val inputStream = context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText()}
    }
}