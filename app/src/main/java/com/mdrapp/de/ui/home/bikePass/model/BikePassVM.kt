package com.mdrapp.de.ui.home.bikePass.model

import com.mdrapp.de.domain.model.bikePass.BikePassDM
import com.mdrapp.de.domain.model.bikePass.BikePassDataDM
import com.mdrapp.de.domain.model.bikePass.CustomerDataDM
import com.mdrapp.de.domain.model.bikePass.InsuranceConditionsDescriptionDM
import com.mdrapp.de.domain.model.bikePass.LeasingAllowedAccessoriesDM
import com.mdrapp.de.domain.model.bikePass.LeasingDataDM
import com.mdrapp.de.domain.model.bikePass.LinkDM
import com.mdrapp.de.domain.model.bikePass.SaveImageLinkDM
import com.mdrapp.de.domain.model.bikePass.ServicePackageDM
import com.mdrapp.de.domain.model.bikePass.ServicePackageDescriptionDM
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

data class BikePassVM(
    val customerData: CustomerDataVM,
    val bikePassData: BikePassDataVM,
    val leasingData: LeasingDataVM
)

data class CustomerDataVM(
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

data class BikePassDataVM(
    val code: String,
    val url: String,
    val saveImageLink: SaveImageLinkVM
)

data class SaveImageLinkVM(
    val label: String,
    val url: String
)

data class LeasingDataVM(
    val insuranceConditions: String,
    val insuranceConditionsDescription: InsuranceConditionsDescriptionVM,
    val servicePackages: List<ServicePackageVM>,
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
    val leasingAllowedAccessories: LeasingAllowedAccessoriesVM
)

data class ServicePackageVM(
    val title: String,
    val description: ServicePackageDescriptionVM
)

data class ServicePackageDescriptionVM(
    val textDescription: String,
    val links: List<LinkVM>
)

data class LinkVM(
    val url: String,
    val label: String
)


data class LeasingAllowedAccessoriesVM(
    val title: String,
    val documentLink: String,
    val documentLabel: String
)

data class InsuranceConditionsDescriptionVM(
    val textDescription: String,
    val links: List<String>
)

fun BikePassDM.toVM(): BikePassVM {
    return BikePassVM(
        customerData = this.customerData.toVM(),
        bikePassData = this.bikePassData.toVM(),
        leasingData = this.leasingData.toVM()
    )
}

fun CustomerDataDM.toVM(): CustomerDataVM {
    return CustomerDataVM(
        id = this.id,
        shippingEmail = this.shippingEmail,
        shippingPhone = this.shippingPhone,
        shippingAddress = getFullAddress(this.shippingAddress, this.shippingPostcode, this.shippingCity),
        shippingPostcode = this.shippingPostcode,
        shippingCity = this.shippingCity,
        createdAt = mapDate(this.createdAt),
        contactPerson = this.contactPerson,
        customerHotline = this.customerHotline
    )
}

fun BikePassDataDM.toVM(): BikePassDataVM {
    return BikePassDataVM(
        code = this.code,
        url = this.url,
        saveImageLink = this.saveImageLink.toVM()
    )
}

fun SaveImageLinkDM.toVM(): SaveImageLinkVM {
    return SaveImageLinkVM(
        label = this.label,
        url = this.url
    )
}

fun LeasingDataDM.toVM(): LeasingDataVM {
    return LeasingDataVM(
        insuranceConditions = this.insuranceConditions,
        insuranceConditionsDescription = this.insuranceConditionsDescription.toVM(),
        servicePackages = this.servicePackages.map { it.toVM() },
        nonAllowedAccessories = this.nonAllowedAccessories,
        orderViaOnlineRetailer = this.orderViaOnlineRetailer,
        mobilityGuarantee = this.mobilityGuarantee,
        allowedBicycleTypes = this.allowedBicycleTypes,
        minMaxPrice = this.minMaxPrice,
        priceBasis = this.priceBasis,
        priceLimitation = this.priceLimitation,
        allowedBicyclesQuantity = this.allowedBicyclesQuantity,
        employerSubsidies = this.employerSubsidies.filterNotNull(),
        allowedLeasingPeriods = this.allowedLeasingPeriods,
        mandatoryAccessories = this.mandatoryAccessories,
        leasingAllowedAccessories = this.leasingAllowedAccessories.toVM()
    )
}

fun ServicePackageDM.toVM(): ServicePackageVM {
    return ServicePackageVM(
        title = this.title,
        description = this.description.toVM()
    )
}

fun ServicePackageDescriptionDM.toVM(): ServicePackageDescriptionVM {
    return ServicePackageDescriptionVM(
        textDescription = this.textDescription,
        links = this.links.map { it.toVM() }
    )
}

fun LinkDM?.toVM(): LinkVM {
    return LinkVM(
        url = this?.url ?: "",
        label = this?.label ?: ""
    )
}

fun LeasingAllowedAccessoriesDM.toVM(): LeasingAllowedAccessoriesVM {
    return LeasingAllowedAccessoriesVM(
        title = this.title,
        documentLink = this.documentLink,
        documentLabel = this.documentLabel
    )
}

fun InsuranceConditionsDescriptionDM.toVM(): InsuranceConditionsDescriptionVM {
    return InsuranceConditionsDescriptionVM(
        textDescription = this.textDescription,
        links = this.links
    )
}

private fun mapDate(inputDate: String): String {
    val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
    inputFormatter.timeZone = TimeZone.getTimeZone("UTC")
    val outputFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val date = inputFormatter.parse(inputDate)
    return outputFormatter.format(date ?: "")
}

private fun getFullAddress(street: String, postcode: String, city: String): String {
    return "$street, $postcode, $city"
}