package com.example.apphl7.domain.model

data class PID(
    val setId: String?,
    val patientIdExtern: String?,
    val patientIdIntern: String?,
    val alternatePatientId: String?,
    val patientName: String?,
    val motherMaidenName: String?,
    val birthDate: String?,
    val sex: String?,
    val patientAlias: String?,
    val race: String?,
    val address: String?,
    val countyCode: String?,
    val phoneHome: String?,
    val phoneBusiness: String?,
    val primaryLanguage: String?,
    val maritalStatus: String?,
    val religion: String?,
    val patientAccountNumber: String?,
    val ssn: String?,
    val driversLicense: String?,
    val mothersIdentifier: String?,
    val ethnicGroup: String?,
    val birthPlace: String?,
    val multipleBirthIndicator: String?,
    val birthOrder: String?,
    val citizenship: String?,
    val veteranStatus: String?,
    val nationality: String?,
    val deathDateTime: String?,
    val deathIndicator: String?
) {
    companion object {
        fun parsePID(segment: HL7Segment): PID {
            val f = segment.fields
            var i = 0
            fun next() = f.getOrNull(i++)

            return PID(
                setId = next(),
                patientIdExtern = next(),
                patientIdIntern = next(),
                alternatePatientId = next(),
                patientName = next(),
                motherMaidenName = next(),
                birthDate = next(),
                sex = next(),
                patientAlias = next(),
                race = next(),
                address = next(),
                countyCode = next(),
                phoneHome = next(),
                phoneBusiness = next(),
                primaryLanguage = next(),
                maritalStatus = next(),
                religion = next(),
                patientAccountNumber = next(),
                ssn = next(),
                driversLicense = next(),
                mothersIdentifier = next(),
                ethnicGroup = next(),
                birthPlace = next(),
                multipleBirthIndicator = next(),
                birthOrder = next(),
                citizenship = next(),
                veteranStatus = next(),
                nationality = next(),
                deathDateTime = next(),
                deathIndicator = next()
            )
        }
    }

}
