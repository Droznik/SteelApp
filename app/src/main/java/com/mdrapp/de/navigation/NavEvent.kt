package com.mdrapp.de.navigation

sealed class NavEvent {
    data class To(
        val route: String,
        val launchSingleTop: Boolean = true,
        val popUpTo: PopUpToAction? = null,
    ) : NavEvent()
    data class BackTo(
        val route: String,
        val inclusive: Boolean = false,
    ) : NavEvent()
    data object Back : NavEvent()
}

sealed class PopUpToAction {
    data class StartDestination(val inclusive: Boolean = false) : PopUpToAction()
    data class FindStartDestination(val inclusive: Boolean = false) : PopUpToAction()
    data class Graph(val inclusive: Boolean = false) : PopUpToAction()
    data class RouteDestination(
        val route: String,
        val inclusive: Boolean = false
    ): PopUpToAction()
}