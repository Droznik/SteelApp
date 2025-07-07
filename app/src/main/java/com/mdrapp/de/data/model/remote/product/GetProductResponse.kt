package com.cbsapp.de.data.model.remote.product

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.cbs.CbsBaseResponse
import com.mdrapp.de.data.model.remote.product.Product

data class GetProductResponse(
    @SerializedName("data") val data: Product? = null
) : CbsBaseResponse()
