package com.example.apphl7.domain.model

data class ObservationGroups(
    val orc: ORC?,
    val obr: OBR?,
    var nte: List<NTE> = emptyList(),
    var obx: List<OBX> = emptyList()
)
