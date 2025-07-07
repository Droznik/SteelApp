package com.mdrapp.de.ui.home.serviceCase.list

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ext.sendEmail
import com.mdrapp.de.ui.home.serviceCase.ServiceCasesEvent
import com.mdrapp.de.ui.home.serviceCase.ServiceCasesState
import com.mdrapp.de.ui.home.serviceCase.ServiceCasesViewModel
import com.mdrapp.de.ui.home.serviceCase.model.ServiceCaseListVM
import com.mdrapp.de.ui.home.serviceCase.model.ServiceCaseVM
import com.mdrapp.de.ui.home.serviceCase.model.ServiceOrderVM
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.HorizontalTextBlock
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.PrimaryHorizontalDivider
import com.mdrapp.de.ui.views.TitleTopBar
import kotlinx.coroutines.launch
const val SUPPORT_EMAIL = "customercare@baronmobil.com"
@Composable
fun ServiceCasesFragment(
    navController: NavController,
    vm: ServiceCasesViewModel = viewModel()
) {
    BaseFragment(vm, navController) {
        val onEvent = remember { vm::onEvent }
        val state by vm.state.collectAsState()
        ServiceCasesContent(state, onEvent)
    }
}

@Composable
fun ServiceCasesContent(
    state: ServiceCasesState,
    onEvent: (ServiceCasesEvent) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TitleTopBar(title = stringResource(R.string.service_case_topbar_title), count = state.serviceCases.count)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            ServiceCaseTabBar(
                selectedTabIndex = state.selectedTab,
                activeOrders = state.serviceCases.activeCases,
                inactiveOrders = state.serviceCases.inactiveCases,
                onTabSelected = { index ->
                    onEvent(ServiceCasesEvent.OnTabSelected(index))
                },
                onEvent = onEvent
            )
        }
        PrimaryButton(
            text = stringResource(R.string.service_case_contact_support),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            context.sendEmail(email = SUPPORT_EMAIL)
        }
    }
}

@Composable
private fun ServiceCaseTabBar(
    selectedTabIndex: Int = 0,
    activeOrders: List<ServiceOrderVM>,
    inactiveOrders: List<ServiceOrderVM>,
    onTabSelected: (index: Int) -> Unit,
    onEvent: (ServiceCasesEvent) -> Unit
) {
    val tabs = listOf(
        stringResource(R.string.service_case_aktiv, activeOrders.sumOf { it.serviceCases.size }),
        stringResource(R.string.service_case_inaktiv, inactiveOrders.sumOf { it.serviceCases.size })
    )


    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            CustomTabRow(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { index ->
                    onTabSelected(index)
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            val items = if (selectedTabIndex == 0) activeOrders else inactiveOrders
            items(items) { order ->
                ServiceOrderItem(serviceOrder = order, onEvent = onEvent)
            }
        }
    }
}

@Composable
private fun ServiceOrderItem(
    serviceOrder: ServiceOrderVM,
    onEvent: (ServiceCasesEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(
            text = serviceOrder.orderNumber,
            style = MDRTheme.typography.title,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
        PrimaryHorizontalDivider()
        serviceOrder.serviceCases.forEach { serviceCase ->
            ServiceCaseItem(serviceCase = serviceCase) {
                onEvent(ServiceCasesEvent.OnServiceCaseClick(serviceCase.id))
            }
        }
    }
}

@Composable
private fun ServiceCaseItem(
    serviceCase: ServiceCaseVM,
    onItemClick: () -> Unit
) {
    BaseServiceOrderItem(onItemClick = onItemClick) {
        HorizontalTextBlock(title = stringResource(R.string.service_case_servicetype), value = serviceCase.serviceType)
        HorizontalTextBlock(title = stringResource(R.string.service_case_status), value = serviceCase.status)
        HorizontalTextBlock(title = stringResource(R.string.service_case_dates), value = serviceCase.timePeriod)
    }
}

@Composable
private fun CustomTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (index: Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(61.dp)
            .background(color = MDRTheme.colors.bg, shape = RoundedCornerShape(36.dp))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tabs.forEachIndexed { index, title ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(36.dp))
                    .clickable {
                        scope.launch {
                            onTabSelected(index)
                        }
                    }
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = title,
                    color = if (selectedTabIndex == index) MDRTheme.colors.primaryText else MDRTheme.colors.secondaryText,
                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun BaseServiceOrderItem(
    action: (@Composable RowScope.() -> Unit)? = null,
    onItemClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .let {
                if (onItemClick != null) it.clickable { onItemClick() } else it
            }
            .padding(top = 5.dp, bottom = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            if (action != null) {
                action()
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            content()
            PrimaryHorizontalDivider()
        }
    }
}

private fun generateMockData(): ServiceCaseListVM {
    val serviceCases1 = listOf(
        ServiceCaseVM(id = 1, serviceType = "Erstinspektion/UVV", status = "Verwendet", timePeriod = "01.04.2023 - 01.03.2024"),
        ServiceCaseVM(id = 2, serviceType = "Wartung inkl. UVV", status = "Inaktiv", timePeriod = "01.04.2025 - 31.03.2026"),
        ServiceCaseVM(id = 3, serviceType = "Wartung inkl. UVV", status = "Abgelaufen", timePeriod = "01.04.2023 - 01.03.2024")
    )

    val serviceCases2 = listOf(
        ServiceCaseVM(id = 4, serviceType = "Sichtprüfung/UVV", status = "Inaktiv", timePeriod = "01.07.2025 - 30.06.2026"),
        ServiceCaseVM(id = 5, serviceType = "Sichtprüfung/UVV", status = "Inaktiv", timePeriod = "01.07.2026 - 30.06.2027")
    )

    val activeOrders = listOf(
        ServiceOrderVM(orderId = 101951, count = 3, orderNumber = "MDR 201555", serviceCases = serviceCases1)
    )

    val inactiveOrders = listOf(
        ServiceOrderVM(orderId = 101962, count = 2, orderNumber = "MDR 201565", serviceCases = serviceCases2)
    )

    return ServiceCaseListVM(
        count = 5,
        activeCases = activeOrders,
        inactiveCases = inactiveOrders
    )
}


@Preview(showBackground = true)
@Composable
private fun ServiceCasesContentPreview() {
    MDRTheme {
        val data = generateMockData()
        ServiceCasesContent(
            state = ServiceCasesState(serviceCases = data),
            onEvent = {}
        )
    }
}