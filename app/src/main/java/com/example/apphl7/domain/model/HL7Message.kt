package com.example.apphl7.domain.model

data class HL7Message(
    val msh: MSH,
    val pid: PID,
    val pvx: PVX,
    val observations: List<ObservationGroups>
)