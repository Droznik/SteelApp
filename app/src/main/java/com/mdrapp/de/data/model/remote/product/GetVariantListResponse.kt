package com.cbsapp.de.data.model.remote.product

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.cbs.CbsBaseResponse
import com.mdrapp.de.data.model.remote.product.ProductVariant
import com.mdrapp.de.data.model.remote.product.toSliderProductDM
import com.mdrapp.de.domain.model.SliderProductDM

open class GetVariantListResponse(
    @SerializedName("data") val data: List<ProductVariant?>? = null
) : CbsBaseResponse()

fun GetVariantListResponse.toSliderProductsDM(): List<SliderProductDM> =
    data?.filterNotNull()?.map { it.toSliderProductDM() }.orEmpty()

