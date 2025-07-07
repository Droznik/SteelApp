package com.mdrapp.de.data.model.remote.purchase

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.domain.model.SetAccessoryDM
import com.mdrapp.de.domain.model.SetPurchaseDM

data class CreateOrderRequest(
    @SerializedName("pre_offer") val preOffer: PreOffer,
    @SerializedName("employee_budget") val employeeBudget: Double?,
    @SerializedName("duration") val duration: Int,
    @SerializedName("inspection_id") val servicePackageId: Long,
)

data class PreOffer(
    @SerializedName("id") val offerId: Long?,
    @SerializedName("bike_manufacturer") val bikeManufacturer: String,
    @SerializedName("bike_name") val bikeName: String,
    @SerializedName("bike_color") val bikeColor: String,
    @SerializedName("bike_type") val bikeType: String,
    @SerializedName("bike_frame_height") val bikeFrameHeight: String,
    @SerializedName("bike_uvp") val bikeUvp: Double,
    @SerializedName("bike_price") val bikePrice: Double,
    @SerializedName("bike_group") val bikeGroup: String,
    @SerializedName("bike_frame_number") val bikeFrameNumber: String?,
    @SerializedName("rahmenart") val frameType: String,
    @SerializedName("monate") val date: String,
    @SerializedName("lagerstatus") val lagerStatus: Boolean?,
    @SerializedName("creditor") val creditor: CreditorRequest,
    @SerializedName("zubehors") val accessories: List<AccessoryRequest>
)

data class CreditorRequest(
    @SerializedName("id") val id: Long
)

data class AccessoryRequest(
    @SerializedName("zubehor_name") val name: String,
    @SerializedName("category") val category: String,
    @SerializedName("price") val price: Double,
    @SerializedName("price_uvp") val priceUvp: Double,
    @SerializedName("quantity") val quantity: Int,
)

fun SetPurchaseDM.toData(): CreateOrderRequest = CreateOrderRequest(
    preOffer = PreOffer(
        offerId = offerId,
        bikeManufacturer = bikeManufacturer,
        bikeName = bikeName,
        bikeColor = bikeColor,
        bikeType = bikeType,
        bikeFrameHeight = bikeFrameHeight,
        bikeUvp = bikeUvp,
        bikePrice = bikePrice,
        bikeGroup = bikeGroup,
        bikeFrameNumber = bikeFrameNumber,
        frameType = frameType,
        date = date,
        lagerStatus = lagerStatus,
        creditor = CreditorRequest(creditorId),
        accessories = accessories.map { it.toData() }
    ),
    employeeBudget = employeeBudget,
    duration = duration,
    servicePackageId = servicePackageId,
)

fun SetAccessoryDM.toData(): AccessoryRequest = AccessoryRequest(
    name = name,
    category = category,
    price = price,
    priceUvp = priceUvp,
    quantity = quantity,
)