package com.mdrapp.de.domain.model.bikePass

data class BikePassDM(
    val customerData: CustomerDataDM,
    val bikePassData: BikePassDataDM,
    val leasingData: LeasingDataDM
)

data class CustomerDataDM(
    val id: Int,
    val shippingEmail: String,
    val shippingPhone: String,
    val shippingAddress: String,
    val shippingPostcode: String,
    val shippingCity: String,
    val createdAt: String,
    val contactPerson: String,
    val customerHotline: String
)

data class BikePassDataDM(
    val code: String,
    val url: String,
    val saveImageLink: SaveImageLinkDM
)

data class SaveImageLinkDM(
    val label: String,
    val url: String
)

data class LeasingDataDM(
    val insuranceConditions: String,
    val insuranceConditionsDescription: InsuranceConditionsDescriptionDM,
    val servicePackages: List<ServicePackageDM>,
    val nonAllowedAccessories: String,
    val orderViaOnlineRetailer: String,
    val mobilityGuarantee: String,
    val allowedBicycleTypes: String,
    val minMaxPrice: String,
    val priceBasis: String,
    val priceLimitation: String,
    val allowedBicyclesQuantity: Int,
    val employerSubsidies: List<String>,
    val allowedLeasingPeriods: String,
    val mandatoryAccessories: String,
    val leasingAllowedAccessories: LeasingAllowedAccessoriesDM
)

data class ServicePackageDM(
    val title: String,
    val description: ServicePackageDescriptionDM
)

data class ServicePackageDescriptionDM(
    val textDescription: String,
    val links: List<LinkDM>
)

data class LinkDM(
    val url: String,
    val label: String
)

data class LeasingAllowedAccessoriesDM(
    val title: String,
    val documentLink: String,
    val documentLabel: String
)
data class InsuranceConditionsDescriptionDM(
    val textDescription: String,
    val links: List<String>
)