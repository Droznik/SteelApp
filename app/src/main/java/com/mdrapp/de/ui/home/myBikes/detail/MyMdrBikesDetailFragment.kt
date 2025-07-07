package com.mdrapp.de.ui.home.myBikes.detail

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ext.browse
import com.mdrapp.de.ext.noRippleClickable
import com.mdrapp.de.ext.sendEmail
import com.mdrapp.de.ui.home.myBikes.MyMdrBikesType
import com.mdrapp.de.ui.home.myBikes.detail.view.MdrOrderAccessoryItem
import com.mdrapp.de.ui.home.myBikes.detail.view.MdrOrderDocItem
import com.mdrapp.de.ui.home.myBikes.detail.view.MdrOrderStatusItem
import com.mdrapp.de.ui.home.myOffers.detail.OrderSubTitle
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.BackTopBar
import com.mdrapp.de.ui.views.HorizontalTextBlock
import com.mdrapp.de.ui.views.PrimaryButtonTopLine
import com.mdrapp.de.ui.views.PrimaryHorizontalDivider
import com.mdrapp.de.ui.views.SecondaryButton


@Composable
fun MyMdrBikesDetailFragment(navController: NavController, vm: MyMdrBikesDetailViewModel) {
    BaseFragment(vm, navController) {
        val state by vm.state.collectAsState()
        val onEvent = remember { vm::onEvent }

        MyMdrBikesDetailContent(state, onEvent)
    }
}

@Composable
fun MyMdrBikesDetailContent(
    state: MyMdrBikesDetailState,
    onEvent: (MyMdrBikesDetailEvent) -> Unit
) {
    val isDocsError: Boolean = remember(state.docs) { state.docs.any { it.employerNote != null } }
    val context = LocalContext.current
    val pdfChooser = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) {
        it?.let { onEvent(MyMdrBikesDetailEvent.UploadDocument(it)) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        DetailBackTopBar(state.type, state.orderNumber) { onEvent(MyMdrBikesDetailEvent.Back) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            ExpandableBlock(
                title = state.orderNumber,
                isExpanded = state.isCommonBlockExpanded,
                onClick = { onEvent(MyMdrBikesDetailEvent.OnCommonBlockClick(state.isCommonBlockExpanded)) }
            ) {
                CommonInfo(state.commonInfo, state.type)
                Spacer(modifier = Modifier.height(38.dp))
                BikeInfo(state.bikeInfo)
                Spacer(modifier = Modifier.height(38.dp))
                AccessoryBlock(accessories = state.accessories)
                PricesBlock(pricesInfo = state.prices)
                Spacer(modifier = Modifier.height(38.dp))
                DealerBlock(dealer = state.dealer)
                Spacer(modifier = Modifier.height(38.dp))
                CalculationBlock(info = state.calculation)
                Spacer(modifier = Modifier.height(38.dp))
                OrderInfoBlock(info = state.orderInfo)
                Spacer(modifier = Modifier.height(56.dp))
            }
            ExpandableBlock(
                title = stringResource(R.string.documents),
                isExpanded = state.isDocumentBlockExpanded,
                count = state.docs.size,
                error = isDocsError,
                onClick = { onEvent(MyMdrBikesDetailEvent.OnDocumentBlockClick(state.isDocumentBlockExpanded)) }
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                DocList(docs = state.docs) { id ->
                    onEvent(MyMdrBikesDetailEvent.GetDocumentUrl(id) { context.browse(it) })
                }
                Spacer(modifier = Modifier.height(32.dp))
                if (state.allowedToUpload && (state.type == MyMdrBikesType.ORDERS)) {
                    SecondaryButton(text = stringResource(R.string.upload_transfer_agreement)) {
                        pdfChooser.launch(arrayOf("application/pdf"))
                    }
                }

            }
            if (state.type == MyMdrBikesType.ORDERS) {
                ExpandableBlock(
                    title = stringResource(R.string.my_mdr_bikes_status_tracking),
                    isExpanded = state.isStatusBlockExpanded,
                    isLast = true,
                    onClick = { onEvent(MyMdrBikesDetailEvent.OnStatusBlockClick(state.isStatusBlockExpanded)) }
                ) {
                    StatusesDescription { onEvent(MyMdrBikesDetailEvent.ShowFAQ) }
                    StatusList(state.trackingStatuses)
                }
            }
        }
        SupportButton(type = state.type, subject = state.orderNumber)
    }
}

@Composable
private fun DetailBackTopBar(type: MyMdrBikesType, number: String, onBack: () -> Unit) {
    BackTopBar(
        title = when (type) {
            MyMdrBikesType.ORDERS -> stringResource(R.string.my_mdr_bikes_toolbar_orders_title)
            MyMdrBikesType.BIKES -> stringResource(R.string.my_mdr_bikes_toolbar_bikes_title)
        },
        subTitle = number
    ) { onBack() }
}

@Composable
private fun ColumnScope.ExpandableBlock(
    title: String,
    isExpanded: Boolean,
    count: Int? = null,
    error: Boolean = false,
    isLast: Boolean = false,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .noRippleClickable { onClick() }
            .padding(top = 15.dp, bottom = 6.dp)
            .padding(horizontal = 20.dp),
    ){
        Text(
            text = title,
            style = MDRTheme.typography.title,
            color = MDRTheme.colors.titleText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(end = 8.dp)
        )
        Box(
            modifier = Modifier.weight(1f)
        ) {
            count?.let {
                Text(
                    text = "[ ${ if (error) "!" else count } ]",
                    style = MDRTheme.typography.regular,
                    fontSize = 19.sp,
                    color = if (error) MDRTheme.colors.error else MDRTheme.colors.titleText,
                )
            }
        }
        Icon(
            imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = MDRTheme.colors.titleText,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
    if(!isLast){
        PrimaryHorizontalDivider(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp))
    }
    if (isExpanded){
        if(isLast) {
            PrimaryHorizontalDivider(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
            content()
        }
    }
    if(!isLast && isExpanded){
        PrimaryHorizontalDivider(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp))
    }
}

@Composable
private fun CommonInfo(info: MdrOrderDetailCommonInfoVM, type: MyMdrBikesType) {
    Spacer(modifier = Modifier.height(8.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ){
        when (type) {
            MyMdrBikesType.BIKES -> {
                HorizontalTextBlock(stringResource(R.string.mdrStatus), info.mdrStatus)
                HorizontalTextBlock(stringResource(R.string.bicycle), info.bikeName)
                HorizontalTextBlock(stringResource(R.string.service_package), info.servicePackage)
                HorizontalTextBlock(stringResource(R.string.leasing_end), info.leasingEnd)
            }

            MyMdrBikesType.ORDERS -> {
                HorizontalTextBlock(stringResource(R.string.mdrStatus), info.mdrStatus)
                HorizontalTextBlock(stringResource(R.string.bicycle), info.bikeName)
                HorizontalTextBlock(stringResource(R.string.my_mdr_bikes_order_dealer), info.dealer)
            }
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
    PrimaryHorizontalDivider()
}

@Composable
private fun BikeInfo(bike: MdrOrderBikeVM) {
    OrderSubTitle(stringResource(R.string.bike))
    Spacer(modifier = Modifier.height(20.dp))
    HorizontalTextBlock(stringResource(R.string.model), bike.model, true)
    HorizontalTextBlock(stringResource(R.string.type), bike.type, true)
    HorizontalTextBlock(stringResource(R.string.brand), bike.brand, true)
    HorizontalTextBlock(stringResource(R.string.frame_type), bike.frameType, true)
    HorizontalTextBlock(stringResource(R.string.category), bike.category, true)
    HorizontalTextBlock(stringResource(R.string.frame_size), bike.frameSize, true)
    HorizontalTextBlock(stringResource(R.string.color), bike.color, true)
    HorizontalTextBlock(stringResource(R.string.amount), bike.quantity, true)
}

@Composable
private fun AccessoryBlock(accessories: List<MdrOrderAccessoryVM>) {
    if (accessories.isNotEmpty()) {
        OrderSubTitle(stringResource(R.string.accessory))
        accessories.forEach {
            Spacer(modifier = Modifier.height(20.dp))
            MdrOrderAccessoryItem(it)
        }
        Spacer(modifier = Modifier.height(38.dp))
    }
}

@Composable
private fun PricesBlock(pricesInfo: MdrOrderPricesVM) {
    OrderSubTitle(stringResource(R.string.total_prices))
    Spacer(modifier = Modifier.height(20.dp))
    HorizontalTextBlock(
        title = stringResource(R.string.total_price_net),
        value = stringResource(R.string.price_euro, pricesInfo.net),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.total_price_gross),
        value = stringResource(R.string.price_euro, pricesInfo.gross),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.gross_list_price_uvp),
        value = stringResource(R.string.price_euro, pricesInfo.uvp),
    )
}

@Composable
private fun DealerBlock(dealer: MdrOrderDealerVM) {
    OrderSubTitle(stringResource(R.string.dealer))
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = buildString {
            if (dealer.name.isNotBlank()) append(dealer.name).append("\n")
            if (dealer.address.isNotBlank()) append(dealer.address)
        },
        style = MDRTheme.typography.medium,
        fontSize = 16.sp,
        color = MDRTheme.colors.secondaryText,
    )
}

@Composable
private fun CalculationBlock(info: MdrOrderCalculationVM) {
    OrderSubTitle(stringResource(R.string.calculation))
    Spacer(modifier = Modifier.height(20.dp))
    HorizontalTextBlock(
        title = stringResource(R.string.leasing_rate),
        value = stringResource(R.string.price_euro, info.leasingRate),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.insurance_rate),
        value = stringResource(R.string.price_euro, info.insuranceRate),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.service_rate),
        value = stringResource(R.string.price_euro, info.serviceRate),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.less_employer_contribution),
        value = stringResource(R.string.price_euro, info.totalAgRate),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.withheld_from_gross_salary),
        value = stringResource(R.string.price_euro, info.totalAnRate),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.my_mdr_bikes_order_tax_rate),
        value = stringResource(R.string.price_euro, info.taxRate),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.pecuniary_advantage),
        value = stringResource(R.string.price_euro, info.benefit)
    )
}

@Composable
private fun OrderInfoBlock(info: MdrOrderInfoVM) {
    OrderSubTitle(stringResource(R.string.my_mdr_bikes_order_info))
    Spacer(modifier = Modifier.height(20.dp))
    HorizontalTextBlock(
        title = stringResource(R.string.leasing_company),
        value = info.leasingCompany,
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.insurance_tariff),
        value = info.insuranceTariff,
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.service_package),
        value = info.servicePackage,
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.delivery_date),
        value = info.deliveryDate,
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.duration),
        value = info.leasingDuration.toString(),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.leasing_end),
        value = info.leasingEnd
    )
}

@Composable
private fun DocList(docs: List<MdrOrderDocVM>, onDocClick: (Long) -> Unit) {
    docs.forEachIndexed { index, doc ->
        MdrOrderDocItem(doc, index, docs.size, onDocClick)
    }
}

@Composable
private fun StatusesDescription(onClick: () -> Unit) {
    val desc = stringResource(R.string.my_mdr_bikes_statuses_desc)
    val link = stringResource(R.string.faq)
    val annotatedString = buildAnnotatedString {
        append(desc)
        append(" ")
        withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
            pushStringAnnotation(tag = link, annotation = link)
            append(link)
            pop()
        }
    }

    Spacer(modifier = Modifier.height(20.dp))
    ClickableText(
        text = annotatedString,
        style = MDRTheme.typography.regular.copy(
            fontSize = 14.sp,
            color = MDRTheme.colors.primaryText
        ),
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = link, start = offset, end = offset)
                .firstOrNull()?.let { onClick() }
        }
    )
}

@Composable
private fun StatusList(statuses: List<MdrOrderTrackingStatusVM>) {
    if (statuses.isNotEmpty()){
        Spacer(modifier = Modifier.height(46.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            statuses.forEach {
                MdrOrderStatusItem(it)
            }
            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

@Composable
private fun SupportButton(type: MyMdrBikesType, subject: String = "") {
    val context = LocalContext.current

    PrimaryButtonTopLine(
        text = stringResource(R.string.support_request),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 13.dp)
    ) {
        context.sendEmail(
            email = when(type) {
                MyMdrBikesType.ORDERS -> "customercare@baronmobil.com"
                MyMdrBikesType.BIKES -> "schaden@baronmobil.com"
            },
            subject = subject
        )
    }
}

@Preview(heightDp = 2000)
@Composable
fun MyMdrBikesDetailPreview() {
    MDRTheme {
        MyMdrBikesDetailContent(
            state = MyMdrBikesDetailState(
                type = MyMdrBikesType.ORDERS,
                orderNumber = "MDR2249427",
                isCommonBlockExpanded = false,
                isDocumentBlockExpanded = true,
                isStatusBlockExpanded = false,
                accessories = listOf(
                    MdrOrderAccessoryVM(
                        id = 0,
                        name = "",
                        quantity = 1,
                        net = 0.0,
                        gross = 0.0,
                        uvp = 0.0,
                    ),
                ),
                docs = listOf(
                    MdrOrderDocVM(
                        id = 1,
                        name = "ÜberlassungsvertragDienstrad_RAGmbH_02122022.pdf",
                        allowedToOpen = true,
                        employerNote = "Antragsstelle hat seinen Antrag zurückgezogen",
                    ),
                    MdrOrderDocVM(
                        id = 1,
                        name = "ÜberlassungsvertragDienstrad_RAGmbH_02122022.pdf",
                        allowedToOpen = true,
                        employerNote = null,
                    ),
                    MdrOrderDocVM(
                        id = 1,
                        name = "ÜberlassungsvertragDienstrad_RAGmbH_02122022.pdf",
                        allowedToOpen = false,
                        employerNote = null,
                    ),
                ),
                allowedToUpload = true,
                dealer = MdrOrderDealerVM("FachHTester", "Testerstr. 6, Oldenburg, 12345, DE")
            ),
            onEvent = {}
        )
    }
}