package com.example.apphl7.presentation.viewmodel

import com.example.apphl7.domain.usecase.ParseHL7FileUseCase


class HL7ViewModel @Inject constructor(
    private val parseHL7FileUseCase: ParseHL7FileUseCase

)