package com.cbsapp.de.data.model.remote.product

import com.google.gson.annotations.SerializedName

data class PagingVariantsResponse(
    @SerializedName("meta") val meta: ResponseMeta? = null
) : GetVariantListResponse()

data class ResponseMeta(
    @SerializedName("current_page") val currentPage: Int,
    @SerializedName("last_page") val lastPage: Int,
)