package com.example.apphl7.domain.model

data class OBRGroup(
    val obr: OBR?, // Observation request segment
    val obx: List<OBX> = emptyList(), // Observation segment
    val nte: List<NTE> = emptyList() //  Notes and comments segment
)
