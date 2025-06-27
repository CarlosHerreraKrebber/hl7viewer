package com.example.apphl7.domain.model

data class OBRGroup(
    val obr: OBR?,
    val obx: List<OBX> = emptyList(),
    val nte: List<NTE> = emptyList()
)
