package com.mdrapp.de.ui.home.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.domain.util.BikeType
import com.mdrapp.de.ui.home.home.model.MdrTab
import com.mdrapp.de.ui.home.home.tabs.bikes.BikesTabFragment
import com.mdrapp.de.ui.home.home.view.HomeTabsRow
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.ProfileTopBar
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch


@Composable
fun ShopFragment(navController: NavController, vm: ShopViewModel) {
    BaseFragment(vm, navController) {
        val state by vm.state.collectAsState()
        val onEvent = remember { vm::onEvent }

        ShopContent(state, navController, onEvent)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShopContent(
    state: ShopState,
    navController: NavController,
    onEvent: (ShopEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    val tabs = remember {
        listOf(
            MdrTab(R.string.shop_tab_e_bikes) {
                BikesTabFragment(
                    navController = navController,
                    vm = hiltViewModel(key = BikeType.Pedelec.name),
                    type = BikeType.Pedelec
                )
            },
            MdrTab(R.string.shop_tab_bikes) {
                BikesTabFragment(
                    navController = navController,
                    vm = hiltViewModel(key = BikeType.Bikes.name),
                    type = BikeType.Bikes
                )
            },
            MdrTab(R.string.shop_tab_recommendations) {  },
            MdrTab(R.string.shop_tab_actions) {  },
        ).toPersistentList()
    }
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val scrollToPage = remember {
        { page: Int -> scope.launch { pagerState.scrollToPage(page) }; Unit }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ProfileTopBar(state.initials) { onEvent(ShopEvent.ShowProfile) }
        Spacer(modifier = Modifier.height(16.dp))
        HomeTabsRow(
            tabs = tabs,
            selected = pagerState.currentPage,
            setSelected = scrollToPage,
            verticalAlignment = Alignment.CenterVertically,
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            userScrollEnabled = false,
            beyondBoundsPageCount = 0
        ) { tabs[it].screen() }
    }
}

@Preview
@Composable
fun ShopPreview() {
    MDRTheme {
        ShopContent(state = ShopState(), rememberNavController(), onEvent = {})
    }
}