package com.mdrapp.de.ui.home.serviceCase.detail

import android.text.Spanned
import android.text.SpannedString
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ext.browse
import com.mdrapp.de.ext.sendEmail
import com.mdrapp.de.ui.home.serviceCase.ServiceCasesEvent
import com.mdrapp.de.ui.home.serviceCase.ServiceCasesState
import com.mdrapp.de.ui.home.serviceCase.ServiceCasesViewModel
import com.mdrapp.de.ui.home.serviceCase.model.BicycleVM
import com.mdrapp.de.ui.home.serviceCase.model.InsuranceRateVM
import com.mdrapp.de.ui.home.serviceCase.model.ServiceCaseDetailVM
import com.mdrapp.de.ui.home.serviceCase.model.ServiceCasePackageVM
import com.mdrapp.de.ui.home.serviceCase.spannedToAnnotatedString
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.BackTopBar
import com.mdrapp.de.ui.views.HorizontalTextBlock
import com.mdrapp.de.ui.views.PrimaryButton

const val DAMAGE_REPORT_EMAIL = "schaden@baronmobil.com"
@Composable
fun ServiceCaseDetailFragment(
    navController: NavController,
    vm: ServiceCasesViewModel
) {
    BaseFragment(vm, navController) {
        val state by vm.state.collectAsState()
        val onEvent = remember { vm::onEvent }
        ServiceCaseDetailContent(state, onEvent)
    }
}

@Composable
private fun ServiceCaseDetailContent(
    state: ServiceCasesState,
    onEvent: (ServiceCasesEvent) -> Unit
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        BackTopBar(
            title = stringResource(id = R.string.service_case_topbar_title),
            subTitle = state.serviceCaseDetail.title
        ) { onEvent(ServiceCasesEvent.OnBackClick) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Title(state.serviceCaseDetail.title)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp)
            ) {
                HorizontalTextBlock(title = stringResource(id = R.string.service_case_status), value = state.serviceCaseDetail.status, true)
                Spacer(modifier = Modifier.height(20.dp))
                ServicePacketBlock(servicePacket = state.serviceCaseDetail.servicePackage,
                    onLinkClicked = { url ->
                        context.browse(url)
                    })
                Spacer(modifier = Modifier.height(16.dp))
                TariffBlock(insuranceRateVM = state.serviceCaseDetail.insuranceRate)
                Spacer(modifier = Modifier.height(16.dp))
                BikeBlock(state.serviceCaseDetail.bicycle)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(R.string.service_case_send_report)
        ) {
            context.sendEmail(email = DAMAGE_REPORT_EMAIL)
        }
    }
}

@Composable
private fun Title(title: String) {
    Text(
        text = title,
        style = MDRTheme.typography.regular,
        fontSize = 43.sp,
        color = MDRTheme.colors.titleText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 8.dp)
    )
    HorizontalDivider(thickness = 1.dp, color = MDRTheme.colors.secondaryText)
}


@Composable
private fun SubTitle(subTitle: String) {
    Text(
        text = subTitle,
        style = MDRTheme.typography.semiBold,
        fontSize = 19.sp,
        color = MDRTheme.colors.titleText,
    )
}

@Composable
private fun BikeBlock(bike: BicycleVM) {
    SubTitle(stringResource(R.string.service_case_bike_info))
    Spacer(modifier = Modifier.height(16.dp))
    HorizontalTextBlock(stringResource(R.string.service_case_manufacturer), bike.manufacturer, true)
    HorizontalTextBlock(stringResource(R.string.service_case_model), bike.model, true)
    HorizontalTextBlock(stringResource(R.string.color), bike.color, true)
    HorizontalTextBlock(stringResource(R.string.service_case_frame_number), bike.frameNumber, false)
}

@Composable
private fun TariffBlock(insuranceRateVM: InsuranceRateVM) {
    SubTitle(stringResource(R.string.service_case_service_tarif))
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = insuranceRateVM.rate,
        style = MDRTheme.typography.medium,
        fontSize = 16.sp,
        color = MDRTheme.colors.secondaryText,
        modifier = Modifier
            .fillMaxWidth()
    )
    ServiceDetailVerticalTextBlock(
        title = stringResource(R.string.service_case_insurance_info),
        value = insuranceRateVM.hintText
    )
}

@Composable
private fun ServicePacketBlock(servicePacket: ServiceCasePackageVM,onLinkClicked: (String) -> Unit) {
    SubTitle(stringResource(R.string.service_case_service_package))
    Spacer(modifier = Modifier.height(16.dp))
    ServiceDetailSpannedVerticalTextBlock(
        title = stringResource(R.string.service_case_service_package_info),
        value = servicePacket.hintText,
        onLinkClicked = { url ->
            onLinkClicked(url)
        }
    )
    HorizontalTextBlock(stringResource(R.string.service_case_order_date), servicePacket.orderDate, true)
    HorizontalTextBlock(stringResource(R.string.service_case_from), servicePacket.fromDate, true)
    HorizontalTextBlock(stringResource(R.string.service_case_to_date), servicePacket.toDate, true)
    HorizontalTextBlock(stringResource(R.string.service_case_service_type), servicePacket.serviceType, false)
}

@Composable
private fun ServiceDetailSpannedVerticalTextBlock(
    title: String,
    value: Spanned,
    onLinkClicked: (String) -> Unit = {}
) {
    Text(
        text = title,
        style = MDRTheme.typography.semiBold,
        fontSize = 16.sp,
        color = MDRTheme.colors.primaryText,
        modifier = Modifier
            .fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(4.dp))
    val annotatedValue = spannedToAnnotatedString(value, MDRTheme.colors.appBlue)
    ClickableText(
        text = annotatedValue ,
        style = MDRTheme.typography.medium.copy(fontSize = 16.sp, color = MDRTheme.colors.secondaryText),
        modifier = Modifier.fillMaxWidth(),
        onClick = { offset ->
            annotatedValue.getStringAnnotations(tag = "URL", start = offset, end = offset).firstOrNull()?.let { annotation ->
                onLinkClicked(annotation.item)
            }
        }
    )
}

@Composable
private fun ServiceDetailVerticalTextBlock(
    title: String,
    value: String,
) {
    Text(
        text = title,
        style = MDRTheme.typography.semiBold,
        fontSize = 16.sp,
        color = MDRTheme.colors.primaryText,
        modifier = Modifier
            .fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = value,
        style = MDRTheme.typography.medium,
        fontSize = 16.sp,
        color = MDRTheme.colors.secondaryText,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
private fun ServiceCaseDetailContentPreview() {
    MDRTheme {
        val mockDetail = ServiceCaseDetailVM(
            id = 1,
            title = "MDR359359",
            status = "Active",
            servicePackage = ServiceCasePackageVM(
                id = 1,
                hintText = SpannedString(""),
                orderDate = "2023-01-01",
                fromDate = "2023-01-01",
                toDate = "2023-12-31",
                serviceType = "Inspection",
            ),
            insuranceRate = InsuranceRateVM(
                id = 1,
                rate = "5%",
                hintText = "Basic Coverage"
            ),
            bicycle = BicycleVM(
                id = 1,
                manufacturer = "Giant",
                model = "Escape 3",
                color = "Blue",
                frameNumber = "123456789"
            )
        )
        val mockState = ServiceCasesState(serviceCaseDetail = mockDetail)
        ServiceCaseDetailContent(
            state = mockState,
            onEvent = {}
        )
    }
}
