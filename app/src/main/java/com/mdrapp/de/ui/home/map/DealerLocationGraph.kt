package com.mdrapp.de.ui.home.map

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mdrapp.de.ext.sharedViewModel
import com.mdrapp.de.navigation.DealerLocationGraph
import com.mdrapp.de.navigation.RootNavHost


fun NavGraphBuilder.dealerLocationGraph(navController: NavController) {
    navigation(startDestination = DealerLocationGraph.DealerLocation.route, route = RootNavHost.DealerLocation.route) {
        composable(DealerLocationGraph.DealerLocation.route) {
            val vm = it.sharedViewModel<StoreMapViewModel>(navController)
            StoreMapFragment(navController = navController, vm = vm)
        }
        composable(DealerLocationGraph.DealerLocationDetails.route) {
            val vm = it.sharedViewModel<StoreMapViewModel>(navController)
            StoreDetailsFragment(navController = navController, vm = vm)
        }
    }
}