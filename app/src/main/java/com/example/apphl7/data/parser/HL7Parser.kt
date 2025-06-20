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

        val msh = rawSegments.find { it.name == "MSH" }?.let { MSH.fillMSH(it) }
        val pid = rawSegments.find { it.name == "PID" }?.let { PID.fillPID(it) }
        val pv1 = rawSegments.find { it.name == "PV1" }?.let { PV1.fillPV1(it) }
        val orc = rawSegments.find { it.name == "ORC" }?.let { ORC.fillORC(it) }

        val obrs = group(rawSegments)
        val observation = ObservationGroups(orc,obrs)
        return HL7Message(
            msh,
            pid,
            pv1,
            observation
        )
    }

    fun group(segment: List<HL7Segment>): List<OBRGroup> {
        val allGroup = mutableListOf<OBRGroup>()
        var currentOBR: OBR? = null
        val currentOBXs = mutableListOf<OBX>()
        val currentNTEs = mutableListOf<NTE>()
        for (seg in segment) {
            when (seg.name) {
                "OBR" -> {
                    if (currentOBR != null) {
                        allGroup.add(OBRGroup(currentOBR, currentOBXs.toList(), currentNTEs.toList()))
                        currentOBXs.clear()
                        currentNTEs.clear()
                    }
                    currentOBR = OBR.fillOBR(seg)
                }
                "OBX" -> currentOBXs.add(OBX.fillOBX(seg))
                "NTE" -> currentNTEs.add(NTE.fillNTE(seg))
            }
        }
        if (currentOBR != null){ // close last open group
            allGroup.add(OBRGroup(currentOBR, currentOBXs, currentNTEs))
        }
        return allGroup
    }
}