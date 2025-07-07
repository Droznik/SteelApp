package com.mdrapp.de.common.fragment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.mdrapp.de.CustomException
import com.mdrapp.de.common.viewmodel.BaseViewModel
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.navigation.PopUpToAction
import com.mdrapp.de.ui.util.collect
import com.mdrapp.de.ui.views.Loader
import com.mdrapp.de.ui.views.PrimarySnackBar
import kotlinx.coroutines.launch

@Composable
fun BaseFragment(
    baseViewModel: BaseViewModel,
    navController: NavController,
    content: @Composable BoxScope.() -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    baseViewModel.error.collect { error ->
        scope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            snackBarHostState.showSnackbar(
                message = when(error) {
                    is CustomException -> error.text.asString(context)
                    else -> error.message ?: error.toString()
                },
                duration = SnackbarDuration.Long
            )
        }
    }

    baseViewModel.message.collect { msg ->
        scope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            snackBarHostState.showSnackbar(
                message = msg.asString(context),
                duration = SnackbarDuration.Long
            )
        }
    }

    baseViewModel.navigate.collect { navEvent ->
        when(navEvent) {
            is NavEvent.Back -> navController.popBackStack()
            is NavEvent.BackTo -> navController.popBackStack(navEvent.route, navEvent.inclusive)
            is NavEvent.To -> {
                navController.navigate(navEvent.route) {
                    navEvent.popUpTo?.let { popUpToAction ->
                        when(popUpToAction) {
                            is PopUpToAction.StartDestination -> {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = popUpToAction.inclusive
                                }
                            }
                            is PopUpToAction.RouteDestination -> {
                                popUpTo(popUpToAction.route) {
                                    inclusive = popUpToAction.inclusive
                                }
                            }
                            is PopUpToAction.FindStartDestination -> {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = popUpToAction.inclusive
                                }
                            }
                            is PopUpToAction.Graph -> {
                                popUpTo(navController.graph.id) {
                                    inclusive = popUpToAction.inclusive
                                }
                            }
                        }
                    }

                    launchSingleTop = navEvent.launchSingleTop
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose { scope.launch { snackBarHostState.currentSnackbarData?.dismiss() } }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        content()
        if (baseViewModel.loading) {
            Loader()
        }
        SnackbarHost(
            modifier = Modifier.align(Alignment.BottomCenter),
            hostState = snackBarHostState
        ) { data ->
            PrimarySnackBar(data)
        }
    }
}