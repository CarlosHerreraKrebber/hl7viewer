package com.example.apphl7.domain.model

data class PVX(
val setId: String?,                                     // PV1-1
val patientClass: String?,                              // PV1-2
val assignedPatientLocation: String?,                   // PV1-3
val admissionType: String?,                             // PV1-4
val preadmitNumber: String?,                            // PV1-5
val priorPatientLocation: String?,                      // PV1-6
val attendingDoctor: String?,                           // PV1-7 (XCN)
val referringDoctor: String?,                           // PV1-8 (XCN)
val consultingDoctor: String?,                          // PV1-9 (XCN) :contentReference[oaicite:1]{index=1}
val hospitalService: String?,                           // PV1-10
val temporaryLocation: String?,                         // PV1-11
val preadmitTestIndicator: String?,                     // PV1-12 :contentReference[oaicite:2]{index=2}
val readmissionIndicator: String?,                      // PV1-13
val admitSource: String?,                               // PV1-14
val ambulatoryStatus: String?,                          // PV1-15
val vipIndicator: String?,                              // PV1-16
val admittingDoctor: String?,                           // PV1-17 (XCN) :contentReference[oaicite:3]{index=3}
val patientType: String?,                               // PV1-18
val visitNumber: String?,                               // PV1-19
)