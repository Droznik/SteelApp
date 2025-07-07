package com.mdrapp.de.domain.model


data class PurchaseInfoDM(
    val customerId: Long,
    val firstName: String?,
    val lastLame: String?,
    val email: String?,
    val gender: String?,
    val bikeTypes: List<String>?,
    val frameTypes: List<String>?,
    val duration: List<Int>?,
    val requiredAccessories: List<RequireAccessoryDM>?,
    val isLeasingAccessories: Boolean?,
    val mdrAccessories: List<String>?,
    val notAllowedAccessories: String?,
    val employeeBudget: Boolean?,
    val servicePackages: List<ServicePackageDM>?,
    val creditors: List<CreditorDM>?
)

data class RequireAccessoryDM(
    val price: Double?,
    val category: String?
)

data class ServicePackageDM(
    val id: Long,
    val title: String?,
    val price: String?
)

data class CreditorDM(
    val id: Long,
    val name: String?
)

data class SetPurchaseDM(
    val offerId: Long?,
    val bikeManufacturer: String,
    val bikeName: String,
    val bikeColor: String,
    val bikeType: String,
    val bikeFrameHeight: String,
    val bikeUvp: Double,
    val bikePrice: Double,
    val bikeGroup: String,
    val bikeFrameNumber: String?,
    val frameType: String,
    val date: String,
    val lagerStatus: Boolean?,
    val creditorId: Long,
    val accessories: List<SetAccessoryDM>,
    val employeeBudget: Double?,
    val duration: Int,
    val servicePackageId: Long,
)

data class SetAccessoryDM(
    val name: String,
    val category: String,
    val price: Double,
    val priceUvp: Double,
    val quantity: Int
)