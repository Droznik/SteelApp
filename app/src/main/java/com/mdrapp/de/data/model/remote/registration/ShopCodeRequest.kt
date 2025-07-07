package com.mdrapp.de.data.model.remote.registration

import com.google.gson.annotations.SerializedName

data class ShopCodeRequest(
    @SerializedName("shop_code")
    val shopCode: String
)