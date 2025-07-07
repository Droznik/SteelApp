package com.mdrapp.de.data.model.remote.mdrOrders

import com.google.gson.annotations.SerializedName

data class UploadOrderFileRequest(
    @SerializedName("order_id") val orderId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("file") val file: UploadFileInfo
)

data class UploadFileInfo(
    @SerializedName("mime") val mime: String,
    @SerializedName("data") val base64: String
)
