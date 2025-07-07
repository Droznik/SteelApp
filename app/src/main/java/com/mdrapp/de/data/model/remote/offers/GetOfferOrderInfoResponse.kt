package com.mdrapp.de.data.model.remote.offers

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.data.model.remote.purchase.Creditor
import com.mdrapp.de.data.model.remote.purchase.ServicePackage
import com.mdrapp.de.data.model.remote.purchase.toDM
import com.mdrapp.de.domain.model.OfferAccessoryDM
import com.mdrapp.de.domain.model.OfferOrderInfoDM

data class GetOfferOrderInfoResponse(
    @SerializedName("response") val response: OfferOrderInfoResponse? = null
) : BaseResponse()

data class OfferOrderInfoResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("customer") val customer: OfferCustomer,
    @SerializedName("isEmployeeBudget") val isEmployeeBudget: Boolean? = null,
    @SerializedName("Fachhändler") val creditor: Creditor? = null,
    @SerializedName("FahrradTyp") val bikeType: String? = null,
    @SerializedName("Rahmenart") val frameType: String? = null,
    @SerializedName("DienstradMarke") val brand: String? = null,
    @SerializedName("DienstradModell") val model: String? = null,
    @SerializedName("DienstradFarbe") val color: String? = null,
    @SerializedName("DienstradSubtyp") val subType: String? = null,
    @SerializedName("Rahmengrobe") val frameSize: String? = null,
    @SerializedName("Rahmennummer") val frameNumber: String? = null,
    @SerializedName("Lieferstatus") val lagerStatus: Boolean? = null,
    @SerializedName("VoraussichtlichesLieferdatum") val date: String? = null,
    @SerializedName("UVP") val uvp: Double? = null,
    @SerializedName("VerkaufspreisDienstradBrutto") val price: Double? = null,
    @SerializedName("Laufzeit") val duration: List<Int>? = null,
    @SerializedName("mdrAccessories") val mdrAccessories: List<String>? = null,
    @SerializedName("Accessories") val accessories: List<OfferAccessory>? = null,
    @SerializedName("LeasingAccessories") val leasingAccessories: List<OfferAccessory>? = null,
    @SerializedName("Servicepaket") val servicePackages: List<ServicePackage>? = null,
    @SerializedName("VerkaufspreisDienstradLeasing") val totalPurchasePrice: Double? = null,
    @SerializedName("Leasingrate") val leasingRate: Double? = null,
    @SerializedName("Versicherungsrate") val insuranceRate: Double? = null,
)

data class OfferCustomer(
    @SerializedName("id") val id: Long,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("gender") val gender: String? = null,
)

data class OfferAccessory(
    @SerializedName("Category") val category: String? = null,
    @SerializedName("Title") val title: String? = null,
    @SerializedName("Quantity") val quantity: Int? = null,
    @SerializedName("UVP") val uvp: Double? = null,
    @SerializedName("Brutto") val price: Double? = null,
)

fun GetOfferOrderInfoResponse.toDomain(): OfferOrderInfoDM = OfferOrderInfoDM(
    id = response?.id!!,
    customerId = response.customer.id,
    isEmployeeBudget = response.isEmployeeBudget,
    creditor = response.creditor?.toDM(),
    firstName = response.customer.firstName,
    lastName = response.customer.lastName,
    email = response.customer.email,
    gender = response.customer.gender,
    bikeType = response.bikeType,
    frameType = response.frameType,
    brand = response.brand,
    model = response.model,
    color = response.color,
    subType = response.subType,
    frameSize = response.frameSize,
    frameNumber = response.frameNumber,
    lagerStatus = response.lagerStatus,
    date = response.date,
    uvp = response.uvp,
    price = response.price,
    duration = response.duration,
    mdrAccessories = response.mdrAccessories,
    accessories = response.accessories?.map { it.toDomain() },
    leasingAccessories = response.leasingAccessories?.map { it.toDomain() },
    servicePackages = response.servicePackages?.map { it.toDM() },
    totalPurchasePrice = response.totalPurchasePrice,
    leasingRate = response.leasingRate,
    insuranceRate = response.insuranceRate,
)

fun OfferAccessory.toDomain(): OfferAccessoryDM = OfferAccessoryDM(
    category = category,
    title = title,
    quantity = quantity,
    uvp = uvp,
    price = price,
)
