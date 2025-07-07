package com.mdrapp.de.data.model.remote.resetPassword

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("recipient") val email: String
)