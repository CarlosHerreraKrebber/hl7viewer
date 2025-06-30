package com.example.apphl7.domain.model

data class ObservationGroups(
    val orc: ORC?, // Common order segment
    val obrGroup: List<OBRGroup> = emptyList() // Observation
    // TODO add pottential NTE here
    )
{
    fun getAbnormalCount(): Int {
       return this.obrGroup.filter { it.obx.first().valueType == "CE" }.count() { obs ->
                                obs.obx.firstOrNull()?.abnormalFlags?.isNotBlank() == true
       }
    }
    // TODO add a fun to count the erhöhte values
    // TODO Notes extraction could be potentionaly refactored to here as extension
}