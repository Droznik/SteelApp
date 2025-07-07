package com.mdrapp.de.ui.home.serviceCase

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mdrapp.de.ext.sharedViewModel
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.MyServiceCasesGraph
import com.mdrapp.de.ui.home.serviceCase.detail.ServiceCaseDetailFragment
import com.mdrapp.de.ui.home.serviceCase.list.ServiceCasesFragment

fun NavGraphBuilder.serviceCaseGraph(navController: NavController) {
    navigation(startDestination = MyServiceCasesGraph.ServiceCases.route, route = HomeNavHost.MyServiceCases.route) {
        composable(MyServiceCasesGraph.ServiceCases.route) {
            val vm = it.sharedViewModel<ServiceCasesViewModel>(navController)
            ServiceCasesFragment(navController = navController, vm = vm)
        }
        composable(MyServiceCasesGraph.ServiceCaseDetail.route) {
            val vm = it.sharedViewModel<ServiceCasesViewModel>(navController)
            ServiceCaseDetailFragment(navController = navController, vm = vm)
        }
    }
}