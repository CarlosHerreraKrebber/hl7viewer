package com.example.apphl7.data.parser

import com.example.apphl7.domain.model.HL7Message
import com.example.apphl7.domain.model.HL7Segment
import com.example.apphl7.domain.model.MSH
import com.example.apphl7.domain.model.NTE
import com.example.apphl7.domain.model.OBR
import com.example.apphl7.domain.model.OBX
import com.example.apphl7.domain.model.ORC
import com.example.apphl7.domain.model.ObservationGroups
import com.example.apphl7.domain.model.PID
import com.example.apphl7.domain.model.PVX

/*
move the parser to the data classes methops -- then it should be callable with .
HWhating all the tutorials as a step back to better under the code
 */

object HL7Parser {
    fun parse(content: String): HL7Message {
        val rawSegments = content.lines().filter { it.isNotBlank() }.map { line ->
            val fields = line.split('|')
            HL7Segment(fields[0], fields.drop(1))
        }

        val msh= rawSegments.find { it.name == "MSH" }?.let { MSH.parseMSH(it) }
        val pid= rawSegments.find { it.name == "PID" }?.let { PID.parsePID(it) }
        val pv1 = rawSegments.find { it.name == "PV1" }?.let { PVX.parsePVX(it) }
          //  ?: throw IllegalArgumentException("PV empty")
        val observations = mutableListOf<ObservationGroups>()

        var currentORC: ORC? = null
        var currentOBR: OBR? = null
        var currentNTEs = mutableListOf<NTE>()
        var currentOBXs = mutableListOf<OBX>()
        for (rawSegment in rawSegments) {
            when (rawSegment.name) {
                "ORC" -> {
                    if (currentORC != null) {
                        observations.add(
                            ObservationGroups(
                                currentORC,
                                currentOBR,
                                currentNTEs,
                                currentOBXs
                            )
                        )
                        currentOBR = null
                        currentNTEs = mutableListOf()
                        currentOBXs = mutableListOf()
                    }
                    currentORC = parseORC(rawSegment)
                }

                "OBR" -> currentOBR = parseOBR(rawSegment)
                "NTE" -> currentNTEs.add(parseNTE(rawSegment))
                "OBX" -> currentOBXs.add(parseOBX(rawSegment))
            }
        }

        if (currentORC != null) {
            observations.add(ObservationGroups(currentORC, currentOBR, currentNTEs, currentOBXs))
        }

        return HL7Message(
            msh,
            pid,
            pv1,
            observations
        )
    }



    fun parsePID(segment: HL7Segment): PID {
        return PID(
            setId = segment.fields.get(0),
            patientId = segment.fields.get(2),
            patientIdentifierList = segment.fields.get(3),
            alternatePatientId = segment.fields.get(4),
            patientName = segment.fields.get(5),
            motherMaidenName = segment.fields.get(6),
            birthDate = segment.fields.get(7),
            sex = segment.fields.get(8),
            patientAlias = segment.fields.get(9),
            race = segment.fields.get(10),
            address = segment.fields.get(11)
        )
    }



    fun parseORC(segment: HL7Segment): ORC {
        return ORC(
            orderControl = segment.fields.getOrNull(0),
            placerOrderNumber = segment.fields.getOrNull(1),
            fillerOrderNumber = segment.fields.getOrNull(2),
            placerGroupNumber = segment.fields.getOrNull(3),
            orderStatus = segment.fields.getOrNull(4),
            responseFlag = segment.fields.getOrNull(5),
            quantityTiming = segment.fields.getOrNull(6),
            parent = segment.fields.getOrNull(7),
            transactionDateTime = segment.fields.getOrNull(8),
            enteredBy = segment.fields.getOrNull(9),
            verifiedBy = segment.fields.getOrNull(10),
            orderingProvider = segment.fields.getOrNull(11),
            entererLocation = segment.fields.getOrNull(12),
            callBackPhoneNumber = segment.fields.getOrNull(13),
            orderEffectiveDateTime = segment.fields.getOrNull(15)
        )
    }

    fun parseOBR(segment: HL7Segment): OBR {
        return OBR(
            setId = segment.fields.getOrNull(0),
            placerOrderNumber = segment.fields.getOrNull(1),
            fillerOrderNumber = segment.fields.getOrNull(2),
            universalServiceId = segment.fields.getOrNull(3),
            priority = segment.fields.getOrNull(4),
            requestedDateTime = segment.fields.getOrNull(6),
            observationDateTime = segment.fields.getOrNull(7),
            observationEndDateTime = segment.fields.getOrNull(8),
            collectionVolume = segment.fields.getOrNull(9),
            collectorIdentifier = segment.fields.getOrNull(10)
        )
    }

    fun parseOBX(segment: HL7Segment): OBX {
        return OBX(
            setId = segment.fields.getOrNull(0),
            valueType = segment.fields.getOrNull(1),
            observationId = segment.fields.getOrNull(2),
            observationSubId = segment.fields.getOrNull(3),
            observationValue = segment.fields.getOrNull(5),
            units = segment.fields.getOrNull(6),
            referenceRange = segment.fields.getOrNull(7),
            abnormalFlags = segment.fields.getOrNull(8),
            probability = segment.fields.getOrNull(9),
            natureOfAbnormalTest = segment.fields.getOrNull(10),
            observationResultStatus = segment.fields.getOrNull(11)
        )
    }

    fun parseNTE(segment: HL7Segment): NTE {
        return NTE(
            setId = segment.fields.getOrNull(0),
            sourceOfComment = segment.fields.getOrNull(1),
            comment = segment.fields.getOrNull(2)
        )
    }

}