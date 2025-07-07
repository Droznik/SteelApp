package com.mdrapp.de.ui.howTo

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mdrapp.de.navigation.HowToGraph
import com.mdrapp.de.navigation.RootNavHost
import com.mdrapp.de.ui.howTo.howTo1.HowTo1Fragment
import com.mdrapp.de.ui.howTo.howTo2.HowTo2Fragment


fun NavGraphBuilder.howToGraph(navController: NavController) {
    navigation(startDestination = HowToGraph.HowTo1.route, route = RootNavHost.HowTo.route) {
        composable(HowToGraph.HowTo1.route) {
            HowTo1Fragment(navController = navController)
        }
        composable(HowToGraph.HowTo2.route) {
            val vm = hiltViewModel<HowToViewModel>()
            HowTo2Fragment(navController = navController, vm = vm)
        }
    }
}