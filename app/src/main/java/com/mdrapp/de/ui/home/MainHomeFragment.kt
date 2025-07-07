package com.mdrapp.de.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mdrapp.de.ui.home.menu.SideMenuFragment
import com.mdrapp.de.ui.theme.MDRTheme
import kotlinx.coroutines.launch


@Composable
fun MainHomeFragment(rootNavController: NavController) {

    val homeNavController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RectangleShape,
                drawerContainerColor = MDRTheme.colors.primaryBackground,
                modifier = Modifier.padding(bottom = 72.dp).then(
                    if (drawerState.targetValue == DrawerValue.Open) Modifier.fillMaxWidth() else Modifier
                )
            ){
                SideMenuFragment(
                    navController = homeNavController,
                    vm = hiltViewModel(),
                    closeDrawer = { scope.launch { drawerState.close() } }
                )
            }
        },
        gesturesEnabled = drawerState.isOpen
    ) {
        Scaffold(
            bottomBar = { BottomMenu(homeNavController) { scope.launch { drawerState.open() }} },
            containerColor = MDRTheme.colors.primaryBackground
        ) {
            HomeNavHost(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                navController = homeNavController,
                rootNavController = rootNavController
            )
        }
    }
}