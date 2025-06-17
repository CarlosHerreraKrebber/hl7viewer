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
import com.example.apphl7.domain.model.PV1

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

        val msh = rawSegments.find { it.name == "MSH" }?.let { MSH.parseMSH(it) }
        val pid = rawSegments.find { it.name == "PID" }?.let { PID.parsePID(it) }
        val pv1 = rawSegments.find { it.name == "PV1" }?.let { PV1.parsePV1(it) }
        val orc = rawSegments.find { it.name == "ORC" }?.let {ORC.parseORC(it)}
          //  ?: throw IllegalArgumentException("PV empty")
        val observations = mutableListOf<ObservationGroups>()
        var currentOrc: ORC? = null
        var obrs = mutableListOf<OBR>()
        var obxs = mutableListOf<OBX>()
        var ntes = mutableListOf<NTE>()

        for (rawSegment in rawSegments) {

        }

        return HL7Message(
            msh,
            pid,
            pv1,
            observations
        )
    }





}