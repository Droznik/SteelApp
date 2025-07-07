package com.mdrapp.de.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mdrapp.de.common.fragment.WebViewFragment
import com.mdrapp.de.common.fragment.WebViewUrls
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.ui.home.bikePass.BikePassFragment
import com.mdrapp.de.ui.home.bikePass.BikePassViewModel
import com.mdrapp.de.ui.home.faq.FaqFragment
import com.mdrapp.de.ui.home.home.ShopFragment
import com.mdrapp.de.ui.home.home.ShopViewModel
import com.mdrapp.de.ui.home.map.dealerLocationGraph
import com.mdrapp.de.ui.home.myBikes.myMdrBikesGraph
import com.mdrapp.de.ui.home.myOffers.myOffersGraph
import com.mdrapp.de.ui.home.order.orderGraph
import com.mdrapp.de.ui.home.profile.ProfileFragment
import com.mdrapp.de.ui.home.profile.ProfileViewModel
import com.mdrapp.de.ui.home.purchase.purchaseGraph
import com.mdrapp.de.ui.home.serviceCase.serviceCaseGraph
import com.mdrapp.de.ui.home.support.SupportFragment


@Composable
fun HomeNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    rootNavController: NavController
) {
    NavHost(
        navController = navController,
        startDestination = HomeNavHost.Home.route,
        modifier = modifier,
    ) {
        composable(HomeNavHost.Home.route) {
            val vm = hiltViewModel<ShopViewModel>()
            ShopFragment(navController, vm)
        }

        purchaseGraph(navController)
        orderGraph(navController)
        myOffersGraph(navController)
        dealerLocationGraph(navController)
        serviceCaseGraph(navController)
        myMdrBikesGraph(navController)

        composable(
            route = HomeNavHost.Product.route,
            arguments = HomeNavHost.Product.arguments
        ) {

        }
        composable(HomeNavHost.Profile.route) {
            val vm = hiltViewModel<ProfileViewModel>()
            ProfileFragment(rootNavController = rootNavController ,navController = navController, vm = vm)}
        composable(HomeNavHost.Favourites.route) {}
        composable(HomeNavHost.Faq.route) { FaqFragment() }
        composable(HomeNavHost.LeasingCalculator.route) {
            WebViewFragment(navController = navController, url = WebViewUrls.CALCULATOR.value)
        }
        composable(HomeNavHost.Support.route) { SupportFragment(navController = navController) }
        composable(HomeNavHost.BikePass.route) {
            val vm = hiltViewModel<BikePassViewModel>()
            BikePassFragment(rootNavController, navController, vm)
        }

        composable(HomeNavHost.TopBikes.route) {
//            val vm = hiltViewModel<CatalogViewModel>()
//            CatalogFragment(navController, vm)
        }
        composable(HomeNavHost.SpecialOffer.route) {
//            val vm = hiltViewModel<CatalogViewModel>()
//            CatalogFragment(navController, vm)
        }
        composable(HomeNavHost.ActionCarousel.route) {
//            val vm = hiltViewModel<CatalogViewModel>()
//            CatalogFragment(navController, vm)
        }
        composable(HomeNavHost.Category.route) {
//            val vm = hiltViewModel<CatalogViewModel>()
//            CatalogFragment(navController, vm)
        }
    }
}