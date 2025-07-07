package com.mdrapp.de.ui.auth.login.forgotPassword

import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.repository.AuthRepository
import com.mdrapp.de.navigation.AuthGraph
import com.mdrapp.de.navigation.NavEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ContractViewModel<ForgotPasswordState, ForgotPasswordEvent>() {

    override val initialState = ForgotPasswordState()

    override fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.OnEmailChange -> _state.update { it.copy(email = event.value) }
            is ForgotPasswordEvent.OnResetPasswordBtnClick -> resetPassword()
            is ForgotPasswordEvent.OnCloseDialogBtnCLicked -> _state.update { it.copy(isEmailVerifiedDialogVisible = false) }
            is ForgotPasswordEvent.OnVerifyEmailBtnClicked -> resendMail()
            is ForgotPasswordEvent.OnLoginBtnClicked -> navigate(NavEvent.BackTo(AuthGraph.Login.route))
        }
    }

    private fun resetPassword() = simpleLaunch {
        val resetPasswordResult = authRepository.resetPassword(state.value.email)
        if (resetPasswordResult.isEmailVerified) {
            navigate(NavEvent.To(route = AuthGraph.SuccessResetPassword.route, true))
        } else {
            _state.update { it.copy(isEmailVerifiedDialogVisible = true) }
        }
    }

    private fun resendMail() = simpleLaunch {
        authRepository.resendEmail(state.value.email)
        _state.update { it.copy(isEmailVerifiedDialogVisible = false) }
    }

}
