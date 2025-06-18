package com.example.apphl7.domain.model

data class PV1(
    val setId: String?,                  // PV1-1
    val patientClass: String?,          // PV1-2
    val assignedPatientLocation: String?, // PV1-3
    val admissionType: String?,         // PV1-4
    val preadmitNumber: String?,        // PV1-5
    val priorPatientLocation: String?,  // PV1-6
    val attendingDoctor: String?,       // PV1-7
    val referringDoctor: String?,       // PV1-8
    val consultingDoctor: String?,      // PV1-9
    val hospitalService: String?,       // PV1-10
    val temporaryLocation: String?,     // PV1-11
    val preadmitTestIndicator: String?, // PV1-12
    val readmissionIndicator: String?,  // PV1-13
    val admitSource: String?,           // PV1-14
    val ambulatoryStatus: String?,      // PV1-15
    val vipIndicator: String?,          // PV1-16
    val admittingDoctor: String?,       // PV1-17
    val patientType: String?,           // PV1-18
    val visitNumber: String?,           // PV1-19
    val financialClass: String?,        // PV1-20
    val chargePriceIndicator: String?,  // PV1-21
    val courtesyCode: String?,          // PV1-22
    val creditRating: String?,          // PV1-23
    val contractCode: String?,          // PV1-24
    val contractEffectiveDate: String?  // PV1-25
) {
    companion object {
        fun fillPV1(segment: HL7Segment): PV1 {
            val f = segment.fields
            var i = 0
            fun next() = f.getOrNull(i++)

            return PV1(
                setId = next(),
                patientClass = next(),
                assignedPatientLocation = next(),
                admissionType = next(),
                preadmitNumber = next(),
                priorPatientLocation = next(),
                attendingDoctor = next(),
                referringDoctor = next(),
                consultingDoctor = next(),
                hospitalService = next(),
                temporaryLocation = next(),
                preadmitTestIndicator = next(),
                readmissionIndicator = next(),
                admitSource = next(),
                ambulatoryStatus = next(),
                vipIndicator = next(),
                admittingDoctor = next(),
                patientType = next(),
                visitNumber = next(),
                financialClass = next(),
                chargePriceIndicator = next(),
                courtesyCode = next(),
                creditRating = next(),
                contractCode = next(),
                contractEffectiveDate = next()
            )
        }
    }
}
