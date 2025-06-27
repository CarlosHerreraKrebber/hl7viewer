package com.example.apphl7.domain.model

data class ObservationGroups(
    val orc: ORC?,
    val obrGroup: List<OBRGroup> = emptyList()
    )
{
    fun getAbnormalCount(): Int {
       return this.obrGroup.filter { it.obx.first().valueType == "CE" }.count() { obs ->
                                obs.obx.firstOrNull()?.abnormalFlags?.isNotBlank() == true
       }
    }
    // TODO add a fun to count the erhöhte values
}