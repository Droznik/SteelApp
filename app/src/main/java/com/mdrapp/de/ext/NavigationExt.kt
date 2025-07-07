package com.mdrapp.de.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel(parentEntry)
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.getBackStackViewModelOrNull(
    navController: NavController,
    route: String
): T? {
    val parentEntry = remember(this) {
        try {
            navController.getBackStackEntry(route)
        } catch (e: Exception) {
            null
        }
    }

    return parentEntry?.let { hiltViewModel(parentEntry) }
}