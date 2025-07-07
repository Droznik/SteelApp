package com.mdrapp.de.data.model.remote.product

import com.google.gson.annotations.SerializedName

data class VariantIdRequest(
    @SerializedName("bike_product_variant_id") val id: Long
)
