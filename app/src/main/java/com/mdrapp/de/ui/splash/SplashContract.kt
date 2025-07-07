package com.mdrapp.de.ui.splash


sealed interface SplashEvent {
    data object Next : SplashEvent
}