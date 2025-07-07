package com.mdrapp.de.ui.home.profile

import androidx.navigation.NavController
import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.repository.AuthRepository
import com.mdrapp.de.domain.repository.UserRepository
import com.mdrapp.de.navigation.RootNavHost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ContractViewModel<ProfileState, ProfileEvent>() {
    override val initialState = ProfileState()

    override fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnTwoFactorAuthClick -> openTwoFactorAuth()
            ProfileEvent.OnChangeRequested -> openChangeRequest()
            is ProfileEvent.OnLogout -> logout(event.rootNavController)
        }
    }

    init {
        simpleLaunch {
            val profileDM = userRepository.getUserProfile()
            _state.update { it.fromDM(profileDM) }
        }
    }

    private fun openTwoFactorAuth() {
        // TODO
    }

    private fun openChangeRequest() {
        // TODO
    }

    private fun logout(rootNavController: NavController) = simpleLaunch {
        authRepository.logout()
        rootNavController.navigate(RootNavHost.Auth.route) {
            popUpTo(rootNavController.graph.id) { inclusive = true }
            launchSingleTop = true
        }
    }
}