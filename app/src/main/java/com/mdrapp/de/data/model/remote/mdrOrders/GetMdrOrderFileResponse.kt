package com.mdrapp.de.data.model.remote.mdrOrders

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse

data class GetMdrOrderFileResponse(
    @SerializedName("response") val response: MdrOrderFileResponse? = null
) : BaseResponse()

data class MdrOrderFileResponse(
    @SerializedName("url") val url: String? = null
)