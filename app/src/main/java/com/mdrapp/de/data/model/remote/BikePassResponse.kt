package com.mdrapp.de.data.model.remote

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
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

data class BikePassResponse(
    @SerializedName("response") val bikePass: BikePass
) : BaseResponse()

data class BikePass(
    @SerializedName("customer_data") val customerData: CustomerData?,
    @SerializedName("bike_pass_data") val bikePassData: BikePassData?,
    @SerializedName("leasing_data") val leasingData: LeasingData?
)

data class CustomerData(
    @SerializedName("id") val id: Int?,
    @SerializedName("shipping_email") val shippingEmail: String?,
    @SerializedName("shipping_phone") val shippingPhone: String?,
    @SerializedName("shipping_address") val shippingAddress: String?,
    @SerializedName("shipping_postcode") val shippingPostcode: String?,
    @SerializedName("shipping_city") val shippingCity: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("contact_person") val contactPerson: String?,
    @SerializedName("customer_hotline") val customerHotline: String?
)

data class BikePassData(
    @SerializedName("code") val code: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("save_image_link") val saveImageLink: SaveImageLink?
)

data class SaveImageLink(
    @SerializedName("label") val label: String?,
    @SerializedName("url") val url: String?
)

data class LeasingData(
    @SerializedName("insurance_conditions") val insuranceConditions: String?,
    @SerializedName("insurance_conditions_description") val insuranceConditionsDescription: InsuranceConditionsDescription?,
    @SerializedName("service_packages") val servicePackages: List<ServicePackage>?,
    @SerializedName("non_allowed_accessories") val nonAllowedAccessories: String?,
    @SerializedName("order_via_online_retailer") val orderViaOnlineRetailer: String?,
    @SerializedName("mobility_guarantee") val mobilityGuarantee: String?,
    @SerializedName("allowed_bicycle_types") val allowedBicycleTypes: String?,
    @SerializedName("min_max_price") val minMaxPrice: String?,
    @SerializedName("price_basis") val priceBasis: String?,
    @SerializedName("price_limitation") val priceLimitation: String?,
    @SerializedName("allowed_bicycles_quantity") val allowedBicyclesQuantity: Int?,
    @SerializedName("employer_subsidies") val employerSubsidies: List<String?>?,
    @SerializedName("allowed_leasing_periods") val allowedLeasingPeriods: String?,
    @SerializedName("mandatory_accessories") val mandatoryAccessories: String?,
    @SerializedName("leasing_allowed_accessories") val leasingAllowedAccessories: LeasingAllowedAccessories?
)

data class ServicePackage(
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: ServicePackageDescription?
)

data class ServicePackageDescription(
    @SerializedName("textDescription") val textDescription: String?,
    @SerializedName("links") val links: List<LinkResponse>?
)

data class LinkResponse(
    @SerializedName("url") val url: String?,
    @SerializedName("label") val label: String?
)

data class LeasingAllowedAccessories(
    @SerializedName("title") val title: String?,
    @SerializedName("document_link") val documentLink: String?,
    @SerializedName("document_label") val documentLabel: String?
)

data class InsuranceConditionsDescription(
    @SerializedName("textDescription") val textDescription: String?,
    @SerializedName("links") val links: List<String>?
)

fun BikePassResponse.toDomain(): BikePassDM {
    return BikePassDM(
        customerData = this.bikePass.customerData?.toDomain() ?: CustomerDataDM(
            id = 0,
            shippingEmail = "",
            shippingPhone = "",
            shippingAddress = "",
            shippingPostcode = "",
            shippingCity = "",
            createdAt = "",
            contactPerson = "",
            customerHotline = ""
        ),
        bikePassData = this.bikePass.bikePassData?.toDomain() ?: BikePassDataDM(
            code = "",
            url = "",
            saveImageLink = SaveImageLinkDM(
                label = "",
                url = ""
            )
        ),
        leasingData = this.bikePass.leasingData?.toDomain() ?: LeasingDataDM(
            insuranceConditions = "",
            insuranceConditionsDescription = InsuranceConditionsDescriptionDM(
                textDescription = "",
                links = emptyList()
            ),
            servicePackages = emptyList(),
            nonAllowedAccessories = "",
            orderViaOnlineRetailer = "",
            mobilityGuarantee = "",
            allowedBicycleTypes = "",
            minMaxPrice = "",
            priceBasis = "",
            priceLimitation = "",
            allowedBicyclesQuantity = 0,
            employerSubsidies = emptyList(),
            allowedLeasingPeriods = "",
            mandatoryAccessories = "",
            leasingAllowedAccessories = LeasingAllowedAccessoriesDM(
                title = "",
                documentLink = "",
                documentLabel = ""
            )
        )
    )
}

fun CustomerData?.toDomain(): CustomerDataDM {
    return CustomerDataDM(
        id = this?.id ?: 0,
        shippingEmail = this?.shippingEmail ?: "",
        shippingPhone = this?.shippingPhone ?: "",
        shippingAddress = this?.shippingAddress ?: "",
        shippingPostcode = this?.shippingPostcode ?: "",
        shippingCity = this?.shippingCity ?: "",
        createdAt = this?.createdAt ?: "",
        contactPerson = this?.contactPerson ?: "",
        customerHotline = this?.customerHotline ?: ""
    )
}

fun BikePassData?.toDomain(): BikePassDataDM {
    return BikePassDataDM(
        code = this?.code ?: "",
        url = this?.url ?: "",
        saveImageLink = this?.saveImageLink?.toDomain() ?: SaveImageLinkDM(
            label = "",
            url = ""
        )
    )
}

fun SaveImageLink?.toDomain(): SaveImageLinkDM {
    return SaveImageLinkDM(
        label = this?.label ?: "",
        url = this?.url ?: ""
    )
}

fun LeasingData?.toDomain(): LeasingDataDM {
    return LeasingDataDM(
        insuranceConditions = this?.insuranceConditions ?: "",
        insuranceConditionsDescription = this?.insuranceConditionsDescription?.toDomain() ?: InsuranceConditionsDescriptionDM(
            textDescription = "",
            links = emptyList()
        ),
        servicePackages = this?.servicePackages?.map { it.toDomain() } ?: emptyList(),
        nonAllowedAccessories = this?.nonAllowedAccessories ?: "",
        orderViaOnlineRetailer = this?.orderViaOnlineRetailer ?: "",
        mobilityGuarantee = this?.mobilityGuarantee ?: "",
        allowedBicycleTypes = this?.allowedBicycleTypes ?: "",
        minMaxPrice = this?.minMaxPrice ?: "",
        priceBasis = this?.priceBasis ?: "",
        priceLimitation = this?.priceLimitation ?: "",
        allowedBicyclesQuantity = this?.allowedBicyclesQuantity ?: 0,
        employerSubsidies = this?.employerSubsidies?.filterNotNull() ?: emptyList(),
        allowedLeasingPeriods = this?.allowedLeasingPeriods ?: "",
        mandatoryAccessories = this?.mandatoryAccessories ?: "",
        leasingAllowedAccessories = this?.leasingAllowedAccessories?.toDomain() ?: LeasingAllowedAccessoriesDM(
            title = "",
            documentLink = "",
            documentLabel = ""
        )
    )
}

fun ServicePackage?.toDomain(): ServicePackageDM {
    return ServicePackageDM(
        title = this?.title ?: "",
        description = this?.description?.toDomain() ?: ServicePackageDescriptionDM(
            textDescription = "",
            links = emptyList()
        )
    )
}

fun ServicePackageDescription?.toDomain(): ServicePackageDescriptionDM {
    return ServicePackageDescriptionDM(
        textDescription = this?.textDescription ?: "",
        links = this?.links?.map { it.toDomain() } ?: emptyList()
    )
}

fun LinkResponse?.toDomain(): LinkDM {
    return LinkDM(
        url = this?.url ?: "",
        label = this?.label ?: ""
    )
}

fun LeasingAllowedAccessories?.toDomain(): LeasingAllowedAccessoriesDM {
    return LeasingAllowedAccessoriesDM(
        title = this?.title ?: "",
        documentLink = this?.documentLink ?: "",
        documentLabel = this?.documentLabel ?: ""
    )
}

fun InsuranceConditionsDescription?.toDomain(): InsuranceConditionsDescriptionDM {
    return InsuranceConditionsDescriptionDM(
        textDescription = this?.textDescription ?: "",
        links = this?.links ?: emptyList()
    )
}