package com.mdrapp.de.domain.model

data class SetCalculationsDM(
    val customerId: Long,
    val creditorId: Long,
    val price: Double,
    val bikeType: String,
    val duration: Int,
    val uvp: Double,
    val employeeBudget: Double?,
    val accessories: List<CalculationAccessoryDM>?,
)

data class CalculationAccessoryDM(
    val uvp: Double,
    val price: Double,
    val quantity: Int,
)

data class CalculationsResultDM(
    val leasingRate: Double?,
    val totalPurchasePrice: Double?,
    val insuranceRate: Double?
)