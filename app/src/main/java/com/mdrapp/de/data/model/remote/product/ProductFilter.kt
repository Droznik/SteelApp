package com.mdrapp.de.data.model.remote.product

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.domain.model.ProductFilterDM

data class ProductFilter(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("internal_name") val internalName: String? = null,
    @SerializedName("de_name") val deName: String? = null,
    @SerializedName("en_name") val enName: String? = null,
    @SerializedName("filter_type") val filterType: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("values") val values: List<String>? = null,
)

fun ProductFilter.toDomain(): ProductFilterDM = ProductFilterDM(
    id = id,
    internalName = internalName,
    deName = deName,
    enName = enName,
    filterType = filterType,
    type = type,
    values = values,
)
