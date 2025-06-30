package com.example.apphl7.domain.model

/* Message Strucure after https://hl7-definition.caristix.com/v2/HL7v2.3/TriggerEvents/ORU_R01
HL7-Definition V2
HL7 v2.3 - ORU_R01 - Unsolicited transmission of an observation message
 */

data class HL7Message(
    val msh: MSH?, // Messeage Head
    val pid: PID?, // Patient IDs
    val pvx: PV1?, // Visit ID
    val pNet: List<NTE> = emptyList(), // Optional Patient Note
    val observations: ObservationGroups // Observation Group
) {
    fun getObrByIndex(setId: Int): OBRGroup {
        return this.observations.obrGroup.first { it.obr?.setId?.toIntOrNull() == setId }
    }

    fun getAufaelligeBefunde(): List<OBRGroup>{
        return this.observations.obrGroup.filter { it.obx.first().valueType == "CE" && it.obx.first().abnormalFlags?.isNotBlank() == true}
    }
}