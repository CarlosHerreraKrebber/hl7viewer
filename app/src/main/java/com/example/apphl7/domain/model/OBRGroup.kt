package com.example.apphl7.domain.model

data class OBRGroup(
    val obr: OBR?,
    var obx: List<OBX> = emptyList(),
    var nte: List<NTE> = emptyList()
)
