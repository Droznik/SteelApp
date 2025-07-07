package com.mdrapp.de.ui.home.myOffers

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mdrapp.de.ext.sharedViewModel
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.MyOffersGraph
import com.mdrapp.de.ui.home.myOffers.detail.OfferDetailFragment
import com.mdrapp.de.ui.home.myOffers.detail.OfferDetailViewModel
import com.mdrapp.de.ui.home.myOffers.list.OfferListFragment
import com.mdrapp.de.ui.home.myOffers.list.OfferListViewModel

fun NavGraphBuilder.myOffersGraph(navController: NavController) {
    navigation(startDestination = MyOffersGraph.OfferList.route, route = HomeNavHost.MyOffers.route) {
        composable(MyOffersGraph.OfferList.route) {
            val vm = it.sharedViewModel<OfferListViewModel>(navController)
            OfferListFragment(navController, vm)
        }
        composable(
            route = MyOffersGraph.OfferDetail.route,
            arguments = MyOffersGraph.OfferDetail.arguments
        ) {
            val vm = hiltViewModel<OfferDetailViewModel>()
            val listVM = it.sharedViewModel<OfferListViewModel>(navController)
            OfferDetailFragment(navController, vm, listVM)
        }
    }
}