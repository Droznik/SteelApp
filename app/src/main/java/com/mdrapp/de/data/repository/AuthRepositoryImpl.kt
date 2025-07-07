package com.mdrapp.de.data.repository

import android.content.Context
import com.mdrapp.de.data.MdrApiService
import com.mdrapp.de.data.model.remote.baseResponse.mdr.handled
import com.mdrapp.de.data.model.remote.login.LoginRequest
import com.mdrapp.de.data.model.remote.login.toDM
import com.mdrapp.de.data.model.remote.registration.ShopCodeRequest
import com.mdrapp.de.data.model.remote.registration.toDomain
import com.mdrapp.de.data.model.remote.resendMail.ResendMailRequest
import com.mdrapp.de.data.model.remote.resendMail.toDM
import com.mdrapp.de.data.model.remote.resetPassword.ResetPasswordRequest
import com.mdrapp.de.data.model.remote.resetPassword.toDM
import com.mdrapp.de.domain.model.auth.login.LoginDataDM
import com.mdrapp.de.domain.model.auth.registration.EmailConfirmationResponseDM
import com.mdrapp.de.domain.model.auth.registration.FinishRegistrationRequestDM
import com.mdrapp.de.domain.model.auth.registration.FinishRegistrationResponseDM
import com.mdrapp.de.domain.model.auth.registration.LastStepResponseDM
import com.mdrapp.de.domain.model.auth.registration.LocationDataDM
import com.mdrapp.de.domain.model.auth.registration.RegistrationRequestDM
import com.mdrapp.de.domain.model.auth.registration.RegistrationResponseDM
import com.mdrapp.de.domain.model.auth.registration.toData
import com.mdrapp.de.domain.model.auth.resendMail.ResendMailDataDM
import com.mdrapp.de.domain.model.auth.resetPassword.ResetPasswordDataDM
import com.mdrapp.de.domain.repository.AuthRepository
import com.mdrapp.de.domain.storage.SessionStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val api: MdrApiService,
    private val sessionStorage: SessionStorage,
    @ApplicationContext private val context: Context
) : AuthRepository {
    override suspend fun getShopCode(shopCode: String): LocationDataDM {
        return api.getShopCode(ShopCodeRequest(shopCode)).handled().toDomain()
    }

    override suspend fun register(registerRequestDM: RegistrationRequestDM): RegistrationResponseDM {
        return api.register(registerRequestDM.toData()).toDomain()
    }

    override suspend fun validateEmail(): EmailConfirmationResponseDM {
        return api.validateEmail().handled().toDomain()
    }

    override suspend fun registerLastStep(): LastStepResponseDM {
        return api.registerLastStep().toDomain()
    }

    override suspend fun finishRegistration(finishRegistrationRequestDM: FinishRegistrationRequestDM): FinishRegistrationResponseDM {
        val registerResponse = api.finishRegistration(finishRegistrationRequestDM.toData()).toDomain()
        registerResponse.response?.let { sessionStorage.setAccessToken(it.accessToken) }
        return registerResponse
    }

    override suspend fun login(email: String, password: String): LoginDataDM {
        val loginRequest = LoginRequest(email, password)
        val loginResponse = api.login(loginRequest).handled().toDM()
        sessionStorage.setAccessToken(loginResponse.accessToken)
        sessionStorage.setToken(loginResponse.token)

        return loginResponse
    }

    override suspend fun resendEmail(email: String): ResendMailDataDM {
        return api.resendMail(ResendMailRequest(email)).handled().toDM()
    }

    override suspend fun resetPassword(email: String): ResetPasswordDataDM {
        return api.resetPassword(ResetPasswordRequest(email)).handled().toDM()
    }

    override suspend fun logout() {
        context.cacheDir.deleteRecursively()
        sessionStorage.logout()
    }
}