package com.mdrapp.de.domain.model

data class OfferOrderInfoDM(
    val id: Long,
    val customerId: Long,
    val isEmployeeBudget: Boolean?,
    val creditor: CreditorDM?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val gender: String?,
    val bikeType: String?,
    val frameType: String?,
    val brand: String?,
    val model: String?,
    val color: String?,
    val subType: String?,
    val frameSize: String?,
    val frameNumber: String?,
    val lagerStatus: Boolean?,
    val date: String?,
    val uvp: Double?,
    val price: Double?,
    val duration: List<Int>?,
    val mdrAccessories: List<String>?,
    val accessories: List<OfferAccessoryDM>?,
    val leasingAccessories: List<OfferAccessoryDM>?,
    val servicePackages: List<ServicePackageDM>?,
    val totalPurchasePrice: Double?,
    val leasingRate: Double?,
    val insuranceRate: Double?
)

data class OfferAccessoryDM(
    val category: String?,
    val title: String?,
    val quantity: Int?,
    val uvp: Double?,
    val price: Double?,
)