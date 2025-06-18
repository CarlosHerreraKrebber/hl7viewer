package com.example.apphl7.domain.model
data class ORC(
    val orderControl: String?,            // ORC-1
    val placerOrderNumber: String?,       // ORC-2
    val fillerOrderNumber: String?,       // ORC-3
    val placerGroupNumber: String?,       // ORC-4
    val orderStatus: String?,             // ORC-5
    val responseFlag: String?,            // ORC-6
    val quantityTiming: String?,          // ORC-7
    val parentOrder: String?,             // ORC-8
    val dateTimeOfTransaction: String?,   // ORC-9
    val enteredBy: String?,               // ORC-10
    val verifiedBy: String?,              // ORC-11
    val orderingProvider: String?,        // ORC-12
    val enterersLocation: String?,        // ORC-13
    val callBackPhoneNumber: String?,     // ORC-14
    val orderEffectiveDateTime: String?,  // ORC-15
    val orderControlCodeReason: String?,  // ORC-16
    val enteringOrganization: String?,    // ORC-17
    val enteringDevice: String?,          // ORC-18
    val actionBy: String?,                // ORC-19
) {
    companion object {
        fun fillORC(segment: HL7Segment): ORC {
            val f = segment.fields
            var i = 0
            fun next() = f.getOrNull(i++)

            return ORC(
                orderControl = next(),
                placerOrderNumber = next(),
                fillerOrderNumber = next(),
                placerGroupNumber = next(),
                orderStatus = next(),
                responseFlag = next(),
                quantityTiming = next(),
                parentOrder = next(),
                dateTimeOfTransaction = next(),
                enteredBy = next(),
                verifiedBy = next(),
                orderingProvider = next(),
                enterersLocation = next(),
                callBackPhoneNumber = next(),
                orderEffectiveDateTime = next(),
                orderControlCodeReason = next(),
                enteringOrganization = next(),
                enteringDevice = next(),
                actionBy = next()
            )
        }
    }
}

