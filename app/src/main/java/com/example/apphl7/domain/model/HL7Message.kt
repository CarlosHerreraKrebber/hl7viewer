package com.example.apphl7.domain.model

data class HL7Message(
    val msh: MSH?,
    val pid: PID?,
    val pvx: PV1?,
    val observations: ObservationGroups
) {
    companion object

    fun getObrByIndex(setId: Int): OBRGroup { //
        return this.observations.obrGroup.first { it.obr?.setId?.toIntOrNull() == setId }
    }
}