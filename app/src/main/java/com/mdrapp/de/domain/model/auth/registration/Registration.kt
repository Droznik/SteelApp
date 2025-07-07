package com.mdrapp.de.domain.model.auth.registration

import com.mdrapp.de.data.model.remote.registration.FinishRegistrationRequest
import com.mdrapp.de.data.model.remote.registration.RegistrationRequest

data class LocationDataDM(
    val locations : List<LocationDM?>
)

data class LocationDM(
    val active: Boolean?,
    val corporation: CorporationDM?,
    val id: Int?,
    val title: String?
)

data class CorporationDM(
    val active: Boolean?,
    val branch: BranchDM?,
    val id: Int?,
    val title: String?
)

data class BranchDM(
    val active: Boolean?,
    val company: CompanyDM?,
    val id: Int?,
    val title: String?
)

data class CompanyDM(
    val active: Boolean?,
    val id: Int?,
    val title: String?
)

data class RegistrationResponseDM(
    val success: Boolean,
    val token: String,
    val usernameError: String,
    val emailError: String,
    val passwordError: String,
    val passwordConfirmationError: String,
    val firstNameError: String,
    val lastNameError: String,
    val genderError: String,
    val termsError: String,
    val locationIdError: String
)
data class RegistrationRequestDM(
    val username: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String,
    val firstName: String,
    val lastName: String,
    val gender: String,
    val locationId: Int,
    val terms: String
)

data class EmailConfirmationResponseDM(
    val response: EmailVerificationDataDM?
)

data class EmailVerificationDataDM(
    val token: String?,
    val isEmailVerified: Boolean?,
    val user: UserDataDM?
)

data class UserDataDM(
    val id: Int?,
    val email: String?
)

data class FinishRegistrationRequestDM(
    val perNo: String,
    val address: String,
    val city: String,
    val postcode: Int,
    val mobilePhone: Int
)

data class LastStepResponseDM(
    val response: LastStepDataDM?
)

data class LastStepDataDM(
    val token: String?,
    val isEmailVerified: Boolean?,
    val company: CompanyDataDM?
)

data class CompanyDataDM(
    val id: Int?,
    val title: String?,
    val address: String?,
    val city: String?,
    val postcode: String?,
    val phoneNumber: String?
)

data class FinishRegistrationResponseDM(
    val response: RegistrationDataDM?,
    val success: Boolean,
    val addressError: String,
    val cityError: String,
    val perNoError: String,
    val phoneError: String,
    val postCodeError: String
)

data class RegistrationDataDM(
    val isEmailVerified: Boolean,
    val finishedRegistration: Boolean,
    val accessToken: String
)


fun RegistrationRequestDM.toData() = RegistrationRequest(
    name = username,
    email = email,
    password = password,
    passwordConfirmation = passwordConfirmation,
    firstName = firstName,
    lastName = lastName,
    gender = gender,
    locationId = locationId,
    terms = terms
)

fun FinishRegistrationRequestDM.toData() = FinishRegistrationRequest(
    perNo = perNo,
    address = address,
    city = city,
    postCode = postcode,
    mobilePhone = mobilePhone
)