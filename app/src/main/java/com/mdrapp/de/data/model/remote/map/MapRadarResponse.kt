package com.mdrapp.de.data.model.remote.map

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.AddressItemDM

data class MapRadarResponse(
    val response: MapItemList
): BaseResponse()

data class MapItemList(
    val list: List<AddressItem>
)

data class AddressItem(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("street") val street: String? = null,
    @SerializedName("zip") val zip: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("website") val website: String? = null,
    @SerializedName("lng") val lng: Double,
    @SerializedName("ltd") val lat: Double
)

fun AddressItem.toDM(data: AddressItem): AddressItemDM = AddressItemDM(
        id = data.id?: -1,
        name = data.name?:"",
        street = data.street?:"",
        zip = data.zip?:"",
        city = data.city?:"",
        phone = data.phone?:"",
        email = data.email?:"",
        website = data.website?:"",
        lng = data.lng,
        lat = data.lat
)

fun MapRadarResponse.toDomain(): List<AddressItemDM> {
    return response.list.map { it.toDM(it) }
}