package com.mdrapp.de.ui.home.myBikes

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.MyMdrBikesGraph
import com.mdrapp.de.ui.home.myBikes.detail.MyMdrBikesDetailFragment
import com.mdrapp.de.ui.home.myBikes.detail.MyMdrBikesDetailViewModel
import com.mdrapp.de.ui.home.myBikes.list.MyMdrBikesFragment
import com.mdrapp.de.ui.home.myBikes.list.MyMdrBikesViewModel


fun NavGraphBuilder.myMdrBikesGraph(navController: NavController) {
    navigation(startDestination = MyMdrBikesGraph.List.route, route = HomeNavHost.MyMdrBikes.route) {
        composable(MyMdrBikesGraph.List.route) {
            val vm = hiltViewModel<MyMdrBikesViewModel>()
            MyMdrBikesFragment(navController, vm)
        }
        composable(
            route = MyMdrBikesGraph.Detail.route,
            arguments = MyMdrBikesGraph.Detail.arguments
        ) {
            val vm = hiltViewModel<MyMdrBikesDetailViewModel>()
            MyMdrBikesDetailFragment(navController, vm)
        }
    }
}