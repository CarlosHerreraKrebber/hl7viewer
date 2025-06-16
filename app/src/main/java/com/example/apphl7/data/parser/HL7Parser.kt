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

object HL7Parser {
    fun parse(content: String): HL7Message {
        val rawSegments = content.lines().filter { it.isNotBlank() }.map { line ->
            val fields = line.split('|')
            HL7Segment(fields[0], fields.drop(1))
        }

        val msh: MSH = rawSegments.find { it.name == "MSH" }?.let {parseMSH(it)}
            ?: error("MSH segment not found or invalid")
        val pid: PID= rawSegments.find { it.name == "PID" }?.let { parsePID(it) }
            ?: throw IllegalArgumentException("PID empty")
        val pv1: PVX = rawSegments.find { it.name == "PVX" }?.let { parsePVX(it) }
            ?: throw IllegalArgumentException("PV empty")
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

    fun parseMSH(segment: HL7Segment): MSH {
        return MSH(
            fieldSeparator = '|',
            encodingChars = segment.fields.getOrElse(0) { "^~&" },
            sendingApp = segment.fields.getOrNull(1),
            sendingFacility = segment.fields.getOrNull(2),
            receivingApp = segment.fields.getOrNull(3),
            receivingFacility = segment.fields.getOrNull(4),
            dateTimeOfMessage = segment.fields.getOrNull(5),
            security = segment.fields.getOrNull(6),
            messageType = segment.fields.getOrNull(7),
            messageControlId = segment.fields.getOrNull(8),
            processingId = segment.fields.getOrNull(9),
            versionId = segment.fields.getOrNull(10),
            sequenceNumber = segment.fields.getOrNull(11),
            continuationPointer = segment.fields.getOrNull(12),
            acceptAcknowledgmentType = segment.fields.getOrNull(13),
            applicationAcknowledgmentType = segment.fields.getOrNull(14),
            countryCode = segment.fields.getOrNull(15)
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

    fun parsePVX(segment: HL7Segment): PVX {
        return PVX(
            setId = segment.fields.getOrNull(0),
            patientClass = segment.fields.getOrNull(1),
            assignedPatientLocation = segment.fields.getOrNull(2),
            admissionType = segment.fields.getOrNull(3),
            preadmitNumber = segment.fields.getOrNull(4),
            priorPatientLocation = segment.fields.getOrNull(5),
            attendingDoctor = segment.fields.getOrNull(6),
            referringDoctor = segment.fields.getOrNull(7),
            consultingDoctor = segment.fields.getOrNull(8),
            hospitalService = segment.fields.getOrNull(9),
            temporaryLocation = segment.fields.getOrNull(10),
            preadmitTestIndicator = segment.fields.getOrNull(11),
            readmissionIndicator = segment.fields.getOrNull(12),
            admitSource = segment.fields.getOrNull(13),
            ambulatoryStatus = segment.fields.getOrNull(14),
            vipIndicator = segment.fields.getOrNull(15),
            admittingDoctor = segment.fields.getOrNull(16),
            patientType = segment.fields.getOrNull(17),
            visitNumber = segment.fields.getOrNull(18)
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