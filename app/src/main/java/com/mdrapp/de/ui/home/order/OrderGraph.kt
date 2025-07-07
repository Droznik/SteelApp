package com.mdrapp.de.ui.home.order

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.OrderGraph

fun NavGraphBuilder.orderGraph(navController: NavController) {
    navigation(startDestination = OrderGraph.Home.route, route = HomeNavHost.Order.route) {
        composable(OrderGraph.Home.route) {

        }
        composable(OrderGraph.Extras.route) {

        }
        composable(OrderGraph.Customer.route) {

        }
        composable(OrderGraph.Delivery.route) {

        }
        composable(OrderGraph.Confirm.route) {

        }
        composable(OrderGraph.Checkout.route) {

        }
        composable(OrderGraph.Result.route) {

        }
        composable(OrderGraph.FinalStep1.route) {

        }
        composable(OrderGraph.FinalStep2.route) {

        }
        composable(OrderGraph.FinalStep3.route) {

        }
    }
}