package com.mdrapp.de.data.model.remote.registration

import com.google.gson.annotations.SerializedName

data class RegistrationRequest(
    @SerializedName("location_id")
    val locationId: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("terms")
    val terms: String
)
