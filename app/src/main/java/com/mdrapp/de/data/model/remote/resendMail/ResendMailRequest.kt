package com.mdrapp.de.data.model.remote.resendMail

import com.google.gson.annotations.SerializedName

data class ResendMailRequest(
    @SerializedName("email") val email: String,
)