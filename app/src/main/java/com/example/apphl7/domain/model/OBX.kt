package com.example.apphl7.domain.model

data class OBX(
    val setId: String?,                    // OBX‑1
    val valueType: String?,                // OBX‑2
    val observationId: String?,           // OBX‑3
    val observationSubId: String?,        // OBX‑4
    val observationValue: String?,        // OBX‑5
    val units: String?,                    // OBX‑6
    val referenceRange: String?,          // OBX‑7
    val abnormalFlags: String?,     // OBX‑8 (repeating)
    val probability: String?,             // OBX‑9
    val natureOfAbnormal: String?,        // OBX‑10
    val resultStatus: String?,            // OBX‑11
    val lastObsNormalValues: String?,     // OBX‑12
    val userAccessChecks: String?,        // OBX‑13
    val dateTimeOfObs: String?,           // OBX‑14
    val producerId: String?,              // OBX‑15
    val responsibleObserver: String?, // OBX‑16 (repeating XCN)
    val observationMethod: String?, // OBX‑17 (repeating CE)
) {
    companion object {
        const val segID = "OBX"

        // see for
        fun fromSegment(segment: HL7Segment): OBX {
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
                abnormalFlags = next(),
                probability = next(),
                natureOfAbnormal = next(),
                resultStatus = next(),
                lastObsNormalValues = next(),
                userAccessChecks = next(),
                dateTimeOfObs = next(),
                producerId = next(),
                responsibleObserver = next(),
                observationMethod = next()
            )
        }
    }

    fun rangeSplit(): List<Float> { // TODO define extenion funktion in the ui layer to scale the rtanges properply
        val refRangeList = mutableListOf<Float>()
        var lowerBound = 25f
        var upperBound = 75f//(this.observationValue?.toFloat()?.times(1.5f))?.coerceAtLeast(1f)!!
        if (this.referenceRange.isNullOrBlank()) {
            refRangeList.add(lowerBound)
            refRangeList.add(upperBound)
        } else {
            when { //TODO move this also to the data class range value ranging funk
                this.referenceRange.contains("-") -> {
                    val parts = this.referenceRange.split("-")
                    lowerBound = parts.firstOrNull()?.toFloat() ?:-1f
                    upperBound = parts.lastOrNull()?.toFloat() ?: -1f
                }

                this.referenceRange.contains(">") -> {
                    lowerBound = this.referenceRange.split(">").lastOrNull()?.toFloat() ?: -1f
                    upperBound = -1f
                }

                this.referenceRange.contains("<") -> {
                    lowerBound = -1f
                    upperBound = this.referenceRange.split("<").lastOrNull()?.toFloat() ?: -1f
                }
            }
            refRangeList.add(lowerBound)
            refRangeList.add(upperBound)
        }

        return refRangeList
    }
}
