package com.mdrapp.de.data.model.remote.serviceCase

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.BicycleDM
import com.mdrapp.de.domain.model.InsuranceRateDM
import com.mdrapp.de.domain.model.ServiceCaseDetailDM
import com.mdrapp.de.domain.model.ServiceCasePackageDM

data class ServiceCaseDetailResponse(
    @SerializedName("response")
    val response: ServiceCaseDetailDara
): BaseResponse()

data class ServiceCaseDetailDara(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("Title")
    val title: String? = null,
    @SerializedName("Status")
    val status: String? = null,
    @SerializedName("Servicepaket")
    val servicePackage: ServicePackage? = null,
    @SerializedName("Versicherungstarif")
    val insuranceRate: InsuranceRate? = null,
    @SerializedName("Fahrrad")
    val bicycle: Bicycle? = null
)

data class ServicePackage(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("Hinweistext")
    val hintText: String? = null,
    @SerializedName("Auftragsdatum")
    val orderDate: String? = null,
    @SerializedName("Von")
    val fromDate: String? = null,
    @SerializedName("Bis")
    val toDate: String? = null,
    @SerializedName("Servicetyp")
    val serviceType: String? = null
)

data class InsuranceRate(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("Tarif")
    val rate: String? = null,
    @SerializedName("Hinweistext")
    val hintText: String? = null
)

data class Bicycle(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("Hersteller")
    val manufacturer: String? = null,
    @SerializedName("Modell")
    val model: String? = null,
    @SerializedName("Farbe")
    val color: String? = null,
    @SerializedName("Rahmennummer")
    val frameNumber: String? = null
)

fun ServiceCaseDetailResponse.toDomain(): ServiceCaseDetailDM {
    return ServiceCaseDetailDM(
        id = this.response.id?:0,
        title = this.response.title?:"",
        status = this.response.status?:"",
        servicePackage = this.response.servicePackage?.toDomain()?: ServiceCasePackageDM(
            id = 0,
            hintText = "",
            orderDate = "",
            fromDate = "",
            toDate = "",
            serviceType = ""
        ),
        insuranceRate = this.response.insuranceRate?.toDomain()?:InsuranceRateDM(
            id = 0,
            rate = "",
            hintText = ""
        ),
        bicycle = this.response.bicycle?.toDomain()?:BicycleDM(
            id = 0,
            manufacturer = "",
            model = "",
            color = "",
            frameNumber = ""
        )
    )
}

fun ServicePackage.toDomain(): ServiceCasePackageDM {
    return ServiceCasePackageDM(
        id = this.id?:0,
        hintText = this.hintText?:"",
        orderDate = this.orderDate?:"",
        fromDate = this.fromDate?:"",
        toDate = this.toDate?:"",
        serviceType = this.serviceType?:""
    )
}

fun InsuranceRate.toDomain(): InsuranceRateDM {
    return InsuranceRateDM(
        id = this.id?:0,
        rate = this.rate?:"",
        hintText = this.hintText?:""
    )
}

fun Bicycle.toDomain(): BicycleDM {
    return BicycleDM(
        id = this.id?:0,
        manufacturer = this.manufacturer?:"",
        model = this.model?:"",
        color = this.color?:"",
        frameNumber = this.frameNumber?:""
    )
}