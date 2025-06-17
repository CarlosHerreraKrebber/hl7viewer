package com.example.apphl7.domain.model
data class OBR(
    val setId: String?,                     // OBR‑1
    val placerOrderNumber: List<String>?,   // OBR‑2 (repeating)
    val fillerOrderNumber: String?,        // OBR‑3
    val universalServiceId: String?,       // OBR‑4
    val priority: String?,                 // OBR‑5
    val requestedDateTime: String?,        // OBR‑6
    val observationDateTime: String?,      // OBR‑7
    val observationEndDateTime: String?,   // OBR‑8
    val collectionVolume: String?,         // OBR‑9
    val collectorIdentifier: List<String>?,// OBR‑10 (repeating)
    val specimenActionCode: String?,       // OBR‑11
    val dangerCode: String?,               // OBR‑12
    val relevantClinicalInfo: String?,     // OBR‑13
    val specimenReceivedDateTime: String?, // OBR‑14
    val specimenSource: String?,           // OBR‑15
    val orderingProvider: List<String>?,   // OBR‑16 (repeating)
    val orderCallbackPhoneNumber: List<String>?, // OBR‑17 (repeating)
    val placerField1: String?,             // OBR‑18
    val placerField2: String?,             // OBR‑19
    val fillerField1: String?,             // OBR‑20
    val fillerField2: String?,             // OBR‑21
    val resultsRptStatusChngDateTime: String?, // OBR‑22
    val chargeToPractice: String?,         // OBR‑23
    val diagnosticServiceSectionId: String?, // OBR‑24
    val resultStatus: String?,             // OBR‑25
    val parentResult: String?,             // OBR‑26
    val quantityTiming: String?,           // OBR‑27
    val resultCopiesTo: List<String>?,     // OBR‑28 (repeating)
    val parentNumber: String?,             // OBR‑29
    val transportationMode: String?,       // OBR‑30
    val reasonForStudy: List<String>?,     // OBR‑31 (repeating)
    val principalResultInterpreter: String?, // OBR‑32
    val assistantResultInterpreter: List<String>?, // OBR‑33 (repeating)
    val technician: List<String>?,         // OBR‑34 (repeating)
    val transcriptionist: List<String>?,   // OBR‑35 (repeating)
    val scheduledDateTime: String?,        // OBR‑36
    val numberOfSampleContainers: String?, // OBR‑37
    val transportLogistics: List<String>?, // OBR‑38 (repeating)
    val collectorsComment: List<String>?,  // OBR‑39 (repeating)
    val transportArrangementResponsibility: String?, // OBR‑40
    val transportArranged: String?,        // OBR‑41
    val escortRequired: String?,           // OBR‑42
    val plannedPatientTransportComment: List<String>? // OBR‑43 (repeating)
) {
    companion object {
        fun parseOBR(segment: HL7Segment): OBR {
            val f = segment.fields
            var i = 0
            fun next() = f.getOrNull(i++)
            fun nextList(separator: String = "~") = next()?.split(separator)
            return OBR(
                setId = next(),
                placerOrderNumber = nextList(),
                fillerOrderNumber = next(),
                universalServiceId = next(),
                priority = next(),
                requestedDateTime = next(),
                observationDateTime = next(),
                observationEndDateTime = next(),
                collectionVolume = next(),
                collectorIdentifier = nextList(),
                specimenActionCode = next(),
                dangerCode = next(),
                relevantClinicalInfo = next(),
                specimenReceivedDateTime = next(),
                specimenSource = next(),
                orderingProvider = nextList(),
                orderCallbackPhoneNumber = nextList(),
                placerField1 = next(),
                placerField2 = next(),
                fillerField1 = next(),
                fillerField2 = next(),
                resultsRptStatusChngDateTime = next(),
                chargeToPractice = next(),
                diagnosticServiceSectionId = next(),
                resultStatus = next(),
                parentResult = next(),
                quantityTiming = next(),
                resultCopiesTo = nextList(),
                parentNumber = next(),
                transportationMode = next(),
                reasonForStudy = nextList(),
                principalResultInterpreter = next(),
                assistantResultInterpreter = nextList(),
                technician = nextList(),
                transcriptionist = nextList(),
                scheduledDateTime = next(),
                numberOfSampleContainers = next(),
                transportLogistics = nextList(),
                collectorsComment = nextList(),
                transportArrangementResponsibility = next(),
                transportArranged = next(),
                escortRequired = next(),
                plannedPatientTransportComment = nextList()
            )
        }
    }
}