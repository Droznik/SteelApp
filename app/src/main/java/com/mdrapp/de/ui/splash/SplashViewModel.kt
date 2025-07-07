package com.mdrapp.de.ui.splash

import androidx.lifecycle.viewModelScope
import com.mdrapp.de.common.viewmodel.BaseViewModel
import com.mdrapp.de.common.viewmodel.EventHandler
import com.mdrapp.de.domain.storage.SessionStorage
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.navigation.PopUpToAction
import com.mdrapp.de.navigation.RootNavHost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sessionStorage: SessionStorage
) : BaseViewModel(), EventHandler<SplashEvent> {

    override fun onEvent(event: SplashEvent) {
        when(event) {
            SplashEvent.Next -> next()
        }
    }

    private fun next() {
        viewModelScope.launch {
            navigate(
                NavEvent.To(
                    route = RootNavHost.Home.route,
                    popUpTo = PopUpToAction.Graph(true)
                )
            )
        }
    }
}