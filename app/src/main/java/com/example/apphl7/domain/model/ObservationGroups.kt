package com.example.apphl7.domain.model

data class ObservationGroups(
    val orc: ORC?,
    val obrGroup: List<OBRGroup>
    )