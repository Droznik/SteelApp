package com.mdrapp.de.data.model.remote.offers

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.OfferListItemDM

data class GetOfferListResponse(
    @SerializedName("response") val response: OfferListResponse? = null
) : BaseResponse()

data class OfferListResponse(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("list") val list: List<OfferListItem>? = null
)

data class OfferListItem(
    @SerializedName("id") val id: Long,
    @SerializedName("AngebotGultigBis") val offerValidUntil: String? = null,
    @SerializedName("Model") val model: String? = null,
    @SerializedName("Marke") val brand: String? = null,
    @SerializedName("KaufpreisGesamt") val totalPrice: Double? = null,
    @SerializedName("Leasingrate") val leasingRate: Double? = null,
    @SerializedName("Fachhandler") val dealer: String? = null,
)

fun GetOfferListResponse.toDomain(): List<OfferListItemDM> = response?.list?.map { it.toDomain() }.orEmpty()

fun OfferListItem.toDomain(): OfferListItemDM = OfferListItemDM(
    id = id,
    offerValidUntil = offerValidUntil,
    model = model,
    brand = brand,
    totalPrice = totalPrice,
    leasingRate = leasingRate,
    dealer = dealer,
)
