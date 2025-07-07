package com.mdrapp.de.ui.auth.login

import com.mdrapp.de.LoginErrorException
import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.model.auth.login.LoginDataDM
import com.mdrapp.de.domain.repository.AuthRepository
import com.mdrapp.de.navigation.AuthGraph
import com.mdrapp.de.navigation.NavEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ContractViewModel<LoginState, LoginEvent>() {

    override val initialState = LoginState()
    override fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.OnEmailChange -> _state.update { it.copy(email = event.value) }
            is LoginEvent.OnPasswordChange -> _state.update { it.copy(password = event.value) }
            is LoginEvent.OnForgotPasswordBtnClicked -> navigate(NavEvent.To(route = AuthGraph.ForgotPassword.route))
            is LoginEvent.OnVerifyEmailBtnClicked -> resendMail()
            is LoginEvent.OnLoginBtnClicked -> login()
            is LoginEvent.OnRegistrationBtnClicked -> navigate(NavEvent.To(AuthGraph.PartnerCode.route))
            is LoginEvent.OnCloseDialogBtnCLicked -> _state.update { it.copy(isEmailVerifiedDialogVisible = false) }
        }
    }

    private fun login() = simpleLaunch {
        if (state.value.email.isBlank() || state.value.password.isBlank()) throw LoginErrorException()
        val loginResult = authRepository.login(state.value.email, state.value.password)
        navigateTo(loginResult)
    }

    private fun navigateTo(loginData: LoginDataDM) {
        if (!loginData.isEmailVerified) {
            _state.update { it.copy(isEmailVerifiedDialogVisible = true) }
        } else if (!loginData.isFinishedRegistration) {
            navigate(NavEvent.To(AuthGraph.LastStep.route))
        } else {
            navigate(NavEvent.To(AuthGraph.MyCategories.route))
        }
    }

    private fun resendMail() = simpleLaunch {
        authRepository.resendEmail(state.value.email)
        _state.update { it.copy(isEmailVerifiedDialogVisible = false) }
    }

}