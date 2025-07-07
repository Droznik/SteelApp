package com.mdrapp.de.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mdrapp.de.navigation.RootNavHost
import com.mdrapp.de.ui.auth.authGraph
import com.mdrapp.de.ui.home.MainHomeFragment
import com.mdrapp.de.ui.howTo.howToGraph
import com.mdrapp.de.ui.splash.SplashFragment
import com.mdrapp.de.ui.splash.SplashViewModel


@Composable
fun RootNavHost() {
    val navController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = RootNavHost.Splash.route
    ) {
        composable(RootNavHost.Splash.route) {
            val vm = hiltViewModel<SplashViewModel>()
            SplashFragment(navController, vm)
        }

        howToGraph(navController)
        authGraph(navController)

        composable(RootNavHost.Home.route) {
            MainHomeFragment(navController)
        }
    }
}