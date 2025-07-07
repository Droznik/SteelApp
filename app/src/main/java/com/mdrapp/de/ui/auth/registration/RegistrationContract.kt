package com.mdrapp.de.ui.auth.registration

import com.mdrapp.de.domain.model.auth.registration.FinishRegistrationRequestDM
import com.mdrapp.de.domain.model.auth.registration.FinishRegistrationResponseDM
import com.mdrapp.de.domain.model.auth.registration.RegistrationRequestDM
import com.mdrapp.de.domain.model.auth.registration.RegistrationResponseDM
import com.mdrapp.de.ui.auth.registration.model.CompanyDataVM
import com.mdrapp.de.ui.auth.registration.model.LocationDataVM
import com.mdrapp.de.ui.auth.registration.model.LocationVM

data class RegistrationState(
    val partnerCode: String = "", val partnerCodeError: Boolean = false,
    val locationData: LocationDataVM? = null,
    val firstName: String = "", val firstNameError: String = "",
    val lastName: String = "", val lastNameError: String = "",
    val email: String = "", val emailError: String = "",
    val password: String = "", val passwordError: String = "",
    val passwordConfirmation: String = "", val passwordConfirmationError: String = "",
    val username: String = "", val usernameError: String = "",
    val gender: String = "", val genderError: String = "",
    val terms: Boolean = false, val termsError: String = "",
    val locationId: Int = 0, val locationError: String = "",
    val locationTitle: String = ""
)

data class RegistrationSecondStepState(
    val lastStepInfoPresent: Boolean = false,
    val company: CompanyDataVM? = null,
    val perNo: String = "", val perNoError: String = "",
    val phone: String = "", val phoneError: String = "",
    val postCode: String = "", val postCodeError: String = "",
    val city: String = "", val cityError: String = "",
    val address: String = "", val addressError: String = ""
)

sealed interface RegistrationEvent {
    data object SendShopCode : RegistrationEvent
    data class OnPartnerCodeChange(val value: String) : RegistrationEvent
    data class OnLocationChange(val value: LocationVM) : RegistrationEvent
    data class OnFirstNameChange(val value: String) : RegistrationEvent
    data class OnLastNameChange(val value: String) : RegistrationEvent
    data class OnEmailChange(val value: String) : RegistrationEvent
    data class OnPasswordChange(val value: String) : RegistrationEvent
    data class OnPasswordConfirmationChange(val value: String) : RegistrationEvent
    data class OnUsernameChange(val value: String) : RegistrationEvent
    data class OnGenderChange(val value: String) : RegistrationEvent
    data class OnTermsChange(val value: Boolean) : RegistrationEvent
    data object OnSubmitRegisterForm : RegistrationEvent
    data object OnToLogin : RegistrationEvent
    data object OnResendEmail : RegistrationEvent
    data object OnValidateEmail : RegistrationEvent
    data object OnNextToFinalStep : RegistrationEvent
}

sealed interface RegistrationSecondStepEvent {
    data object OnConfirmFinalStep : RegistrationSecondStepEvent
    data class OnPersonalNumberChange(val value: String) : RegistrationSecondStepEvent
    data class OnPhoneChange(val value: String) : RegistrationSecondStepEvent
    data class OnPostCodeChange(val value: String) : RegistrationSecondStepEvent
    data class OnCityChange(val value: String) : RegistrationSecondStepEvent
    data class OnAddressChange(val value: String) : RegistrationSecondStepEvent
}

fun RegistrationState.fromDM(
    registrationDM: RegistrationResponseDM
) = this.copy(
    usernameError = registrationDM.usernameError,
    emailError = registrationDM.emailError,
    passwordError = registrationDM.passwordError,
    passwordConfirmationError = registrationDM.passwordConfirmationError,
    firstNameError = registrationDM.firstNameError,
    lastNameError = registrationDM.lastNameError,
    genderError = registrationDM.genderError,
)

fun RegistrationSecondStepState.fromDM(
    finishRegistrationResponseDM: FinishRegistrationResponseDM
) = this.copy(
    cityError = finishRegistrationResponseDM.cityError,
    addressError = finishRegistrationResponseDM.addressError,
    phoneError = finishRegistrationResponseDM.phoneError,
    perNoError = finishRegistrationResponseDM.perNoError,
    postCodeError = finishRegistrationResponseDM.postCodeError
)

fun RegistrationState.toDM() = RegistrationRequestDM(
    username = this.username,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    gender = this.gender,
    locationId = this.locationId,
    password = this.password,
    passwordConfirmation = this.passwordConfirmation,
    terms = this.terms.toString()
)

fun RegistrationSecondStepState.toDM() = FinishRegistrationRequestDM(
    perNo = this.perNo,
    address = this.address,
    city = this.city,
    mobilePhone = this.phone.toInt(),
    postcode = this.postCode.toInt()
)