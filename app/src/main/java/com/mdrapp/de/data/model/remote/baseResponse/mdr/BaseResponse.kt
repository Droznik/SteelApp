package com.mdrapp.de.data.model.remote.baseResponse.mdr

import com.google.gson.annotations.SerializedName


open class BaseResponse {
    @SerializedName("success") val success: Boolean? = null
    @SerializedName("errors") val errors: Map<String?, List<String?>?>? = null
}


inline fun <reified T : BaseResponse> T.handled(): T {
    if (success == false) {
        val msg = errors?.values
            ?.firstOrNull()
            ?.filterNotNull()
            ?.joinToString(separator = ", ")

        when {
            !msg.isNullOrBlank() -> throw RuntimeException(msg)
            else -> throw RuntimeException("Oops!")
        }
    }

    return this
}