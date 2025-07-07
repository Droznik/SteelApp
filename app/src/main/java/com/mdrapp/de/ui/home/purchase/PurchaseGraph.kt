package com.mdrapp.de.ui.home.purchase

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.PurchaseGraph
import com.mdrapp.de.ui.home.purchase.order.PurchaseOrderFragment
import com.mdrapp.de.ui.home.purchase.order.PurchaseOrderViewModel
import com.mdrapp.de.ui.home.purchase.result.PurchaseResultFragment
import com.mdrapp.de.ui.home.purchase.variant.PurchaseVariantFragment


fun NavGraphBuilder.purchaseGraph(navController: NavController) {
    navigation(startDestination = PurchaseGraph.Variant.route, route = HomeNavHost.Purchase.route) {
        composable(PurchaseGraph.Variant.route) { PurchaseVariantFragment(navController) }
        composable(
            route = PurchaseGraph.Order.route,
            arguments = PurchaseGraph.Order.arguments
        ) {
            val vm = hiltViewModel<PurchaseOrderViewModel>()
            PurchaseOrderFragment(navController, vm)
        }
        composable(PurchaseGraph.Result.route) { PurchaseResultFragment(navController) }
    }
}