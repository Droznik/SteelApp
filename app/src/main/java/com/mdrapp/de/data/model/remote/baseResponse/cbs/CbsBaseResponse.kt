package com.mdrapp.de.data.model.remote.baseResponse.cbs

import com.google.gson.annotations.SerializedName


open class CbsBaseResponse {
    @SerializedName("message") val message: String? = null
    @SerializedName("errors") val errors: Map<String?, List<String?>?>? = null
}


inline fun <reified T : CbsBaseResponse> T.handled(): T {
    errors?.let {
        it.values.firstOrNull()?.let { errorList ->
            errorList.filterNotNull().joinToString(separator = ", ").let { msg ->
                throw RuntimeException(msg)
            }
        }
    }

    return this
}