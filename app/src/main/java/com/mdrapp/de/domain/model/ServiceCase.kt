package com.mdrapp.de.domain.model

data class ServiceCaseListDM(
    val count: Int,
    val activeCases: List<ServiceOrderDM>,
    val inactiveCases: List<ServiceOrderDM>
)

data class ServiceOrderDM(
    val orderId: Int,
    val count: Int,
    val orderNumber: String,
    val serviceCases: List<ServiceCaseDM>
)

data class ServiceCaseDM(
    val id: Int,
    val serviceType: String,
    val status: String,
    val timePeriod: String
)

data class ServiceCaseDetailDM(
    val id: Int,
    val title: String,
    val status: String,
    val servicePackage: ServiceCasePackageDM,
    val insuranceRate: InsuranceRateDM,
    val bicycle: BicycleDM
)

data class ServiceCasePackageDM(
    val id: Int,
    val hintText: String,
    val orderDate: String,
    val fromDate: String,
    val toDate: String,
    val serviceType: String
)

data class InsuranceRateDM(
    val id: Int,
    val rate: String,
    val hintText: String
)

data class BicycleDM(
    val id: Int,
    val manufacturer: String,
    val model: String,
    val color: String,
    val frameNumber: String
)