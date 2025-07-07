package com.mdrapp.de.ui.home.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.navigation.DealerLocationGraph
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.ui.home.myBikes.MyMdrBikesType
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.ProfileTopBar


@Composable
fun SideMenuFragment(
    navController: NavController,
    vm: SideMenuViewModel,
    closeDrawer: () -> Unit
) {
    BaseFragment(vm, navController) {
        val state by vm.state.collectAsState()
        val onEvent = remember { vm::onEvent }

        SideMenuContent(state, onEvent, closeDrawer)
    }
}

@Composable
fun SideMenuContent(
    state: SideMenuState,
    onEvent: (SideMenuEvent) -> Unit,
    closeDrawer: () -> Unit
) {
    val navigatePopUpHome = remember {
        { route: String ->
            onEvent(SideMenuEvent.NavigatePopUpToHome(route))
            closeDrawer()
        }
    }

    Column(Modifier.fillMaxSize()) {
        ProfileTopBar {
            navigatePopUpHome(HomeNavHost.Profile.route)
        }
        PrimaryButton(
            text = stringResource(R.string.menu_order_bike),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            onEvent(SideMenuEvent.OnPurchaseClick { closeDrawer() })
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 50.dp, bottom = 45.dp)
                .padding(horizontal = 16.dp)
        ) {
            MenuItem(stringResource(R.string.menu_my_offers)) { navigatePopUpHome(HomeNavHost.MyOffers.route) }
            MenuSpacer()
            MenuItem(stringResource(R.string.menu_my_orders)) { navigatePopUpHome(HomeNavHost.MyMdrBikes.createRoute(MyMdrBikesType.ORDERS)) }
            MenuSpacer()
            MenuItem(stringResource(R.string.menu_my_bikes)) { navigatePopUpHome(HomeNavHost.MyMdrBikes.createRoute(MyMdrBikesType.BIKES)) }
            MenuSpacer()
            MenuItem(stringResource(R.string.menu_my_service_cases)) {navigatePopUpHome(HomeNavHost.MyServiceCases.route)}
            MenuSpacer()
            MenuItem(stringResource(R.string.menu_my_favourites)) {}
            MenuSpacer(40)
            MenuItem(stringResource(R.string.menu_dealer_search)) { navigatePopUpHome(DealerLocationGraph.DealerLocation.route) }
            MenuSpacer()
            MenuItem(stringResource(R.string.menu_leasing_calculator)) { navigatePopUpHome(HomeNavHost.LeasingCalculator.route) }
            MenuSpacer()
            MenuItem(stringResource(R.string.menu_faq)) { navigatePopUpHome(HomeNavHost.Faq.route) }
            MenuSpacer()
            MenuItem(stringResource(R.string.menu_support)) { navigatePopUpHome(HomeNavHost.Support.route) }
            MenuSpacer()
        }
    }
}

@Composable
private fun MenuItem(title: String, count: Int = 0, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {
        Text(
            text = title,
            style = MDRTheme.typography.regular,
            fontSize = 32.sp,
            color = MDRTheme.colors.titleText,
            modifier = Modifier.weight(1f, false)
        )
        if (count > 0) {
            Text(
                text = "[ $count ]",
                style = MDRTheme.typography.regular,
                fontSize = 19.sp,
                color = MDRTheme.colors.titleText,
                modifier = Modifier
                    .padding(start = 5.dp)
            )
        }

    }
}

@Composable
private fun MenuSpacer(height: Int = 10) {
    Spacer(modifier = Modifier.height(height.dp))
}

@Preview
@Composable
private fun SideMenuPreview() {
    MDRTheme {
        SideMenuContent(SideMenuState(), {}) {}
    }
}