package com.example.apphl7.domain.model

import androidx.compose.ui.layout.SubcomposeSlotReusePolicy

data class HL7Message(
    val msh: MSH?,
    val pid: PID?,
    val pvx: PV1?,
    val observations: ObservationGroups
) {
    fun getObrByIndex(setId: Int): OBRGroup {
        return this.observations.obrGroup.first { it.obr?.setId?.toIntOrNull() == setId }
    }

    fun getAufaelligeBefunde(): List<OBRGroup>{
        return this.observations.obrGroup.filter { it.obx.first().valueType == "CE" && it.obx.first().abnormalFlags?.isNotBlank() == true}
    }
}