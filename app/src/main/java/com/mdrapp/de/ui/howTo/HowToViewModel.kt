package com.mdrapp.de.ui.howTo

import com.mdrapp.de.common.viewmodel.BaseViewModel
import com.mdrapp.de.common.viewmodel.EventHandler
import com.mdrapp.de.domain.storage.SessionStorage
import com.mdrapp.de.navigation.AuthGraph
import com.mdrapp.de.navigation.NavEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HowToViewModel @Inject constructor(
    private val sessionStorage: SessionStorage
) : BaseViewModel(), EventHandler<HowToEvent> {
    override fun onEvent(event: HowToEvent) {
        when (event) {
            is HowToEvent.NavigateToLogin -> navigateToLogin()
        }
    }

    private fun navigateToLogin() = simpleLaunch {
        sessionStorage.completeHowTo(true)
        navigate(NavEvent.To(AuthGraph.Login.route))
    }

}