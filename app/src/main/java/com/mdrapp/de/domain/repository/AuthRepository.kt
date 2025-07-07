package com.mdrapp.de.domain.repository

import com.mdrapp.de.domain.model.auth.login.LoginDataDM
import com.mdrapp.de.domain.model.auth.registration.EmailConfirmationResponseDM
import com.mdrapp.de.domain.model.auth.registration.FinishRegistrationRequestDM
import com.mdrapp.de.domain.model.auth.registration.FinishRegistrationResponseDM
import com.mdrapp.de.domain.model.auth.registration.LastStepResponseDM
import com.mdrapp.de.domain.model.auth.registration.LocationDataDM
import com.mdrapp.de.domain.model.auth.registration.RegistrationRequestDM
import com.mdrapp.de.domain.model.auth.registration.RegistrationResponseDM
import com.mdrapp.de.domain.model.auth.resendMail.ResendMailDataDM
import com.mdrapp.de.domain.model.auth.resetPassword.ResetPasswordDataDM


interface AuthRepository {
    suspend fun getShopCode(shopCode: String): LocationDataDM

    suspend fun register(registerRequestDM: RegistrationRequestDM): RegistrationResponseDM

    suspend fun validateEmail(): EmailConfirmationResponseDM

    suspend fun registerLastStep(): LastStepResponseDM

    suspend fun finishRegistration(finishRegistrationRequestDM: FinishRegistrationRequestDM): FinishRegistrationResponseDM

    suspend fun logout()
    suspend fun login(email: String, password: String): LoginDataDM
    suspend fun resendEmail(email: String): ResendMailDataDM
    suspend fun resetPassword(email: String): ResetPasswordDataDM
}