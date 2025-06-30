package com.example.apphl7.data.parser

import com.example.apphl7.domain.model.HL7Message
import com.example.apphl7.domain.model.HL7Segment
import com.example.apphl7.domain.model.MSH
import com.example.apphl7.domain.model.NTE
import com.example.apphl7.domain.model.OBR
import com.example.apphl7.domain.model.OBRGroup
import com.example.apphl7.domain.model.OBX
import com.example.apphl7.domain.model.ORC
import com.example.apphl7.domain.model.ObservationGroups
import com.example.apphl7.domain.model.PID
import com.example.apphl7.domain.model.PV1

/*

 */

object HL7Parser {
    fun parse(content: String): HL7Message {
        val rawSegments = content.lines().filter { it.isNotBlank() }.map { line ->
            val fields = line.split('|')
            HL7Segment(fields[0], fields.drop(1))
        }

        val msh = rawSegments.find { it.name == MSH.segID }?.let { MSH.fromSegment(it) }
        val pid = rawSegments.find { it.name == PID.segID }?.let { PID.fromSegment(it) }
        val pv1 = rawSegments.find { it.name == PV1.segID }?.let { PV1.fromSegment(it) }
        val orc = rawSegments.find { it.name == ORC.segID }?.let { ORC.fromSegment(it) }

        val (obrs,pNet) = group(rawSegments)
        val observation = ObservationGroups(orc,obrs)
        return HL7Message(
            msh,
            pid,
            pv1,
            pNet,
            observation
        )
    }
//TODO add a catch when its NTE didderentciate the NTE to eaterh PID or OCR level

    fun group(segment: List<HL7Segment>): Pair<List<OBRGroup>, List<NTE>>  {
        val allGroup = mutableListOf<OBRGroup>()
        var currentOBR: OBR? = null
        val currentOBXs = mutableListOf<OBX>()
        val currentNTEs = mutableListOf<NTE>()
        val patientNTEs = mutableListOf<NTE>()
        for (seg in segment) {
            when (seg.name) {
                OBR.segID -> {
                    // Checks for OBR seg ocurrance and addes the observation(obx) and note(note)
                    if (currentOBR != null) {
                        allGroup.add(OBRGroup(currentOBR, currentOBXs.toList(), currentNTEs.toList()))
                        currentOBXs.clear()
                        currentNTEs.clear()
                    }
                    currentOBR = OBR.fromSegment(seg)
                }
                OBX.segID -> currentOBXs.add(OBX.fromSegment(seg))
                NTE.segID -> {
                    // Stores NTE in a patient NTE level list if occurence befor OBR
                    if (currentOBR != null) {
                        currentNTEs.add(NTE.fromSegment(seg))
                    }
                    patientNTEs.add(NTE.fromSegment(seg))
                }
            }
        }
        if (currentOBR != null){ // close last open group
            allGroup.add(OBRGroup(currentOBR, currentOBXs, currentNTEs))
        }
        return Pair(allGroup, patientNTEs)
    }
}