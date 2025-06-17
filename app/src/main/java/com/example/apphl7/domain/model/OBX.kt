package com.example.apphl7.domain.model

data class OBX(
    val setId: String?,                    // OBX‑1
    val valueType: String?,                // OBX‑2
    val observationId: String?,           // OBX‑3
    val observationSubId: String?,        // OBX‑4
    val observationValue: String?,        // OBX‑5
    val units: String?,                    // OBX‑6
    val referenceRange: String?,          // OBX‑7
    val abnormalFlags: List<String>?,     // OBX‑8 (repeating)
    val probability: String?,             // OBX‑9
    val natureOfAbnormal: String?,        // OBX‑10
    val resultStatus: String?,            // OBX‑11
    val lastObsNormalValues: String?,     // OBX‑12
    val userAccessChecks: String?,        // OBX‑13
    val dateTimeOfObs: String?,           // OBX‑14
    val producerId: String?,              // OBX‑15
    val responsibleObserver: List<String>?, // OBX‑16 (repeating XCN)
    val observationMethod: List<String>?, // OBX‑17 (repeating CE)
    val equipmentInstanceId: String?,     // OBX‑18 (v2.5+, included for completeness)
    val analysisDateTime: String?,        // OBX‑19 (optional, some versions)
    val observationSite: List<String>?    // OBX‑20 (repeating CWE)
) {
    companion object {
        fun parseOBX(segment: HL7Segment): OBX {
            val f = segment.fields
            var i = 0
            fun next() = f.getOrNull(i++)
            fun nextList(separator: String = "~") = next()?.split(separator)

            return OBX(
                setId = next(),
                valueType = next(),
                observationId = next(),
                observationSubId = next(),
                observationValue = next(),
                units = next(),
                referenceRange = next(),
                abnormalFlags = nextList(),
                probability = next(),
                natureOfAbnormal = next(),
                resultStatus = next(),
                lastObsNormalValues = next(),
                userAccessChecks = next(),
                dateTimeOfObs = next(),
                producerId = next(),
                responsibleObserver = nextList(),
                observationMethod = nextList(),
                equipmentInstanceId = next(),     // Not in v2.3, but often added in implementations
                analysisDateTime = next(),        // OBX‑19 (by some extensions)
                observationSite = nextList()
            )
        }
    }
}