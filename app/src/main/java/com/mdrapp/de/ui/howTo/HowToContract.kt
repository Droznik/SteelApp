package com.mdrapp.de.ui.howTo

sealed interface HowToEvent {
    data object NavigateToLogin : HowToEvent
}