package com.example.apphl7.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apphl7.domain.model.HL7Message
import com.example.apphl7.domain.model.OBRGroup
import com.example.apphl7.domain.usecase.LoadAndParseHL7FileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HL7ViewModel @Inject constructor(
    private val loadAndParseHL7File: LoadAndParseHL7FileUseCase
) : ViewModel() {

    var parsedMessage by mutableStateOf<HL7Message?>(null)
        private set

    fun loadHL7(context: android.content.Context) {
        viewModelScope.launch(Dispatchers.IO) {
            parsedMessage = loadAndParseHL7File(context, "Beispiel HL7.hl7")
        }
    }

}
