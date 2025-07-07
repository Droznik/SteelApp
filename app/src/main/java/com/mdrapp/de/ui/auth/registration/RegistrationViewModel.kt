package com.mdrapp.de.ui.auth.registration

import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.repository.AuthRepository
import com.mdrapp.de.domain.storage.SessionStorage
import com.mdrapp.de.navigation.AuthGraph
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.navigation.PopUpToAction
import com.mdrapp.de.ui.auth.registration.model.toVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val sessionStorage: SessionStorage,
    private val authRepository: AuthRepository
) : ContractViewModel<RegistrationState, RegistrationEvent>() {
    override val initialState = RegistrationState()

    private val initialSecondStepState = RegistrationSecondStepState()
    private val _secondStepState: MutableStateFlow<RegistrationSecondStepState> by lazy {
        MutableStateFlow(
            initialSecondStepState
        )
    }
    val secondStepState: StateFlow<RegistrationSecondStepState> get() = _secondStepState

    override fun onEvent(event: RegistrationEvent) {
        when (event) {
            is RegistrationEvent.OnPartnerCodeChange -> {
                _state.update { it.copy(partnerCode = event.value) }
            }

            RegistrationEvent.SendShopCode -> sendShopCode()
            is RegistrationEvent.OnEmailChange -> {
                _state.update { it.copy(email = event.value) }
            }

            is RegistrationEvent.OnFirstNameChange -> {
                _state.update { it.copy(firstName = event.value) }
            }

            is RegistrationEvent.OnGenderChange -> {
                _state.update { it.copy(gender = event.value) }
            }

            is RegistrationEvent.OnLastNameChange -> {
                _state.update { it.copy(lastName = event.value) }
            }

            is RegistrationEvent.OnLocationChange -> {
                _state.update { it.copy(locationId = event.value.id ?: 0) }
                _state.update { it.copy(locationTitle = event.value.title ?: "") }
            }

            is RegistrationEvent.OnPasswordChange -> {
                _state.update { it.copy(password = event.value) }
            }

            is RegistrationEvent.OnPasswordConfirmationChange -> {
                _state.update { it.copy(passwordConfirmation = event.value) }
            }

            is RegistrationEvent.OnTermsChange -> {
                _state.update { it.copy(terms = event.value) }
            }

            is RegistrationEvent.OnUsernameChange -> {
                _state.update { it.copy(username = event.value) }
            }

            RegistrationEvent.OnToLogin -> navigate(NavEvent.BackTo(AuthGraph.Login.route))
            RegistrationEvent.OnSubmitRegisterForm -> register()
            RegistrationEvent.OnResendEmail -> resendEmail()
            RegistrationEvent.OnValidateEmail -> validateEmail()
            RegistrationEvent.OnNextToFinalStep -> navigate(NavEvent.To(AuthGraph.LastStep.route))
        }
    }

    fun onSecondStepEvent(event: RegistrationSecondStepEvent) {
        when (event) {
            RegistrationSecondStepEvent.OnConfirmFinalStep -> finishRegistration()
            is RegistrationSecondStepEvent.OnCityChange -> {
                _secondStepState.update { it.copy(city = event.value) }
            }

            is RegistrationSecondStepEvent.OnPersonalNumberChange -> {
                _secondStepState.update { it.copy(perNo = event.value) }
            }

            is RegistrationSecondStepEvent.OnPhoneChange -> {
                _secondStepState.update { it.copy(phone = event.value) }
            }

            is RegistrationSecondStepEvent.OnPostCodeChange -> {
                _secondStepState.update { it.copy(postCode = event.value) }
            }

            is RegistrationSecondStepEvent.OnAddressChange -> {
                _secondStepState.update { it.copy(address = event.value) }
            }

        }
    }

    private fun sendShopCode() {
        simpleLaunch {
            val response = authRepository.getShopCode(state.value.partnerCode)
            if (response.locations.isNotEmpty()) {
                _state.update {
                    it.copy(locationData = response.toVM())
                }
                navigate(NavEvent.To(AuthGraph.Register.route))
            }
        }
    }

    private fun validateEmail() {
        simpleLaunch {
            val response = authRepository.validateEmail()
            if (response.response?.token?.isNotEmpty() == true) {
                sessionStorage.setToken(response.response.token)
                navigateToConfirmEmailSuccess()
            }
        }
    }

    private fun navigateToConfirmEmail() {
        navigate(
            NavEvent.To(
                route = AuthGraph.EmailConfirm.route,
                popUpTo = PopUpToAction.Graph(true)
            )
        )
    }

    private fun navigateToConfirmEmailSuccess() {
        navigate(
            NavEvent.To(
                route = AuthGraph.EmailConfirmSuccess.route,
                popUpTo = PopUpToAction.Graph(true)
            )
        )
    }

    fun getFinalStep() {
        if (!secondStepState.value.lastStepInfoPresent) {
            simpleLaunch {
                val response = authRepository.registerLastStep()
                if (response.response?.token?.isNotEmpty() == true) {
                    sessionStorage.setAccessToken(response.response.token)
                    _secondStepState.update { it.copy(
                        lastStepInfoPresent = true,
                        company = response.response.company?.toVM()) }
                }
            }
        }
    }

    private fun finishRegistration() {
        simpleLaunch {
            val response = authRepository.finishRegistration(
                secondStepState.value.toDM()
            )
            if (response.success) {
                navigate(NavEvent.To(AuthGraph.MyCategories.route, true, popUpTo = PopUpToAction.RouteDestination(AuthGraph.Login.route)))
            } else {
                _secondStepState.update { it.fromDM(response) }
            }
        }
    }

    private fun register() {
        simpleLaunch {
            val response = authRepository.register(
                state.value.toDM()
            )
            if (response.success) {
                sessionStorage.setToken(response.token)
                navigateToConfirmEmail()
            } else {
                _state.update { it.fromDM(response) }
            }
        }
    }

    private fun resendEmail() = simpleLaunch{
        authRepository.resendEmail(state.value.email)
    }

}