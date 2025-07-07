package com.mdrapp.de.ui.home.bikePass

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ext.browse
import com.mdrapp.de.ext.noRippleClickable
import com.mdrapp.de.ext.saveBitmapToGallery
import com.mdrapp.de.ext.shareBitmap
import com.mdrapp.de.ui.home.bikePass.model.BikePassDataVM
import com.mdrapp.de.ui.home.bikePass.model.CustomerDataVM
import com.mdrapp.de.ui.home.bikePass.model.LeasingDataVM
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.BackTopBar
import com.mdrapp.de.ui.views.HorizontalTextBlock
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.SecondaryButton
import com.mdrapp.de.ui.views.TextBlockDivider
import com.mdrapp.de.ui.views.TitleTopBar
import com.mdrapp.de.ui.views.VerticalTextBlockWithSubtitle

@Composable
fun BikePassFragment(
    rootNavController: NavController,
    navController: NavController,
    vm: BikePassViewModel
) {
    BaseFragment(baseViewModel = vm, navController = navController) {
        val onEvent = remember { vm::onEvent }
        val state by vm.state.collectAsState()

        BikePassContent(rootNavController, state, onEvent)
    }

}

@Composable
private fun BikePassContent(
    rootNavController: NavController,
    state: BikePassState,
    onEvent: (BikePassEvent) -> Unit
) {
    var isQrCodeExpanded by remember { mutableStateOf(false) }
    var isInfoBlockExpanded by remember { mutableStateOf(false) }
    val qrCodeSize by animateDpAsState(targetValue = if (isQrCodeExpanded) 250.dp else 150.dp,
        label = ""
    )
    var expandedItem by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(expandedItem, onBack = { expandedItem = "" })
        if (expandedItem.isBlank()) {
            BikePassHeader()
        }
        if (isInfoBlockExpanded) {
            InfoBlockPopup { isInfoBlockExpanded = !isInfoBlockExpanded }
        }
        if (expandedItem.isBlank()) {
            state.bikePassData?.let { BikePassDetails(it) }
            BikePassQrCode(state = state, isQrCodeExpanded = isQrCodeExpanded, qrCodeSize = qrCodeSize,
                onQrCodeClick = {
                    isQrCodeExpanded = !isQrCodeExpanded
                },
                onInfoBlockClick = {
                    isInfoBlockExpanded = !isInfoBlockExpanded
                }
            )
            Spacer(Modifier.weight(1f))
        }
        ExpandableSections(rootNavController, !isQrCodeExpanded, expandedItem, onExpand = { expandedItem = it }, state, onEvent)
        QrCodeActions(isQrCodeExpanded, state.bikePassData)
    }
}

@Composable
fun InfoBlockPopup(
    onCloseIconClick: () -> Unit
) {
    Dialog(onDismissRequest = { onCloseIconClick() }) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = MDRTheme.colors.primaryBackground,
            modifier = Modifier.fillMaxWidth()
        ) {
            InfoBlockView(onCloseIconClick = onCloseIconClick)
        }
    }
}

@Composable
private fun TopBar(expandedItem: String, onBack: () -> Unit) {
    if (expandedItem.isBlank()) {
        TitleTopBar(title = stringResource(id = R.string.bike_pass_toolbar_title))
    } else {
        BackTopBar(
            title = stringResource(id = R.string.bike_pass_toolbar_title),
            onBack = onBack
        )
    }
}

@Composable
private fun BikePassHeader() {
    Spacer(Modifier.height(22.dp))
    Title(title = stringResource(id = R.string.bike_pass_screen_title))
    Spacer(Modifier.height(8.dp))
    HorizontalDivider(
        thickness = 1.dp,
        color = MDRTheme.colors.secondaryText,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(8.dp))
}

@Composable
private fun BikePassDetails(bikePassData: BikePassDataVM) {
    Row(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            modifier = Modifier.weight(0.5f),
            text = stringResource(id = R.string.bike_pass_code),
            color = MDRTheme.colors.secondaryText,
            style = MDRTheme.typography.medium,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier.weight(0.5f),
            text = bikePassData.code,
            color = MDRTheme.colors.titleText,
            style = MDRTheme.typography.medium,
            fontSize = 16.sp,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun BikePassQrCode(
    state: BikePassState,
    isQrCodeExpanded: Boolean,
    qrCodeSize: Dp,
    onQrCodeClick: () -> Unit,
    onInfoBlockClick: () -> Unit
) {
    Box(modifier = Modifier.height(100.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AnimatedVisibility(
                visible = isQrCodeExpanded,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 14.dp, end = 20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    modifier = Modifier.noRippleClickable { onQrCodeClick() },
                    contentDescription = null
                )
            }
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        AnimatedVisibility(
            state.bikePassData?.url?.isNotBlank() ?: false,
            Modifier.align(Alignment.CenterHorizontally)
        ) {
            Column {
                QrCode(
                    value = state.bikePassData?.url ?: "",
                    modifier = Modifier
                        .size(qrCodeSize)
                        .noRippleClickable { onQrCodeClick() }
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = null,
                    modifier = Modifier
                        .noRippleClickable { onInfoBlockClick() }
                        .padding(4.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun QrCodeActions(isQrCodeExpanded: Boolean, bikePassData: BikePassDataVM?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if(isQrCodeExpanded) {
            var bitmap by remember { mutableStateOf<Bitmap?>(null) }
            val context = LocalContext.current
            Column {
                PrimaryButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.bike_pass_save_image)
                ) {
                    try {
                        bitmap = BarcodeEncoder().encodeBitmap(bikePassData?.url, BarcodeFormat.QR_CODE, 512, 512)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Firebase.crashlytics.recordException(e)
                    }
                    bitmap?.let { context.saveBitmapToGallery(it) }
                }
                Spacer(Modifier.height(10.dp))
                PrimaryButton(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    text = stringResource(id = R.string.bike_pass_share_qr_code)
                ) {
                    try {
                        bitmap = BarcodeEncoder().encodeBitmap(bikePassData?.url, BarcodeFormat.QR_CODE, 512, 512)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Firebase.crashlytics.recordException(e)
                    }
                    bitmap?.let { context.shareBitmap(it) }
                }
                Spacer(Modifier.height(13.dp))
            }
        }
    }
}

@Composable
private fun ExpandableSections(
    rootNavController: NavController,
    isQrCodeExpanded: Boolean,
    expandedItem: String,
    onExpand: (String) -> Unit,
    state: BikePassState,
    onEvent: (BikePassEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        if (isQrCodeExpanded) {
            Spacer(Modifier.height(7.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                ExpandableTextView(
                    viewId = "leasingView",
                    title = stringResource(id = R.string.bike_pass_leasing),
                    expandedItem = expandedItem,
                    onExpand = onExpand,
                    isDividerVisible = true,
                    content = {
                        //state.leasingData?.let { LeasingData(locationData = it) }
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .let { if (expandedItem == "leasingView") it.weight(1f) else it }) {
                    AnimatedVisibility(
                        visible = expandedItem == "leasingView",
                        enter = fadeIn(animationSpec = tween(1600))
                    ) {
                        state.leasingData?.let { LeasingData(locationData = it) }
                    }
                }
                ExpandableTextView(
                    viewId = "contactView",
                    title = stringResource(id = R.string.bike_pass_my_data),
                    expandedItem = expandedItem,
                    onExpand = onExpand,
                    isDividerVisible = false,
                    content = {
                        //state.customerData?.let { MyData(it, onEvent, rootNavController) }
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .let { if (expandedItem == "contactView") it.weight(1f) else it }) {
                    AnimatedVisibility(
                        visible = expandedItem == "contactView",
                        enter = fadeIn(animationSpec = tween(1600))
                    ) {
                        state.customerData?.let { MyData(it, onEvent, rootNavController) }
                    }
                }
            }
        }
    }
}

@Composable
private fun Title(title: String) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = title,
        color = MDRTheme.colors.titleText,
        style = MDRTheme.typography.title
    )
}

@Composable
private fun ColumnScope.LeasingData(locationData: LeasingDataVM) {
    Spacer(Modifier.height(24.dp))
    Column(
        Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(horizontal = 16.dp)
            .clickable(enabled = false, onClick = {})
            .verticalScroll(rememberScrollState())
    ) {
        VerticalTextBlockWithSubtitle(
            title = stringResource(id = R.string.bike_pass_insurance_conditions_for_download),
            subtitle = locationData.insuranceConditions,
            value = locationData.insuranceConditionsDescription.textDescription
        )
        if (locationData.servicePackages.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.bike_pass_service_package_including_services),
                style = MDRTheme.typography.medium,
                fontSize = 16.sp,
                color = MDRTheme.colors.secondaryText,
                modifier = Modifier.fillMaxWidth()
            )
            locationData.servicePackages.map { servicePackage ->
                Text(
                    text = servicePackage.title,
                    style = MDRTheme.typography.medium,
                    fontSize = 16.sp,
                    color = MDRTheme.colors.titleText,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Text(
                    text = servicePackage.description.textDescription,
                    style = MDRTheme.typography.regular,
                    fontSize = 14.sp,
                    color = MDRTheme.colors.secondaryText,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                val context = LocalContext.current
                servicePackage.description.links.map { linkData ->
                    val label = buildAnnotatedString {
                        withStyle(SpanStyle(color = MDRTheme.colors.linkText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, textDecoration = TextDecoration.Underline)) {
                            append(linkData.label)
                        }
                    }
                    ClickableText(
                        text = label,
                        style = MDRTheme.typography.regular
                    ) {
                        context.browse(linkData.url)
                    }
                }
                Spacer(Modifier.height(5.dp))
            }
            TextBlockDivider()
        }
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_prohibited_accessories),
            value = locationData.nonAllowedAccessories,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_order_via_online_retailer),
            value = locationData.orderViaOnlineRetailer,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_mobility_guarantee),
            value = locationData.mobilityGuarantee,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_permitted_bicycle_types),
            value = locationData.allowedBicycleTypes,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_min_max_price),
            value = locationData.minMaxPrice,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_basis_of_price_limitation),
            value = locationData.priceBasis,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_price_limit),
            value = locationData.priceLimitation,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_number_of_permitted_wheels),
            value = locationData.allowedBicyclesQuantity.toString(),
            isDivider = true
        )
        Text(
            text = stringResource(id = R.string.bike_pass_employer_subsidies),
            style = MDRTheme.typography.regular,
            fontSize = 14.sp,
            color = MDRTheme.colors.secondaryText,
        )
        locationData.employerSubsidies.map { subsidy ->
            Text(
                text = subsidy,
                style = MDRTheme.typography.medium,
                fontSize = 16.sp,
                color = MDRTheme.colors.titleText,
            )
        }
        TextBlockDivider()
        Spacer(Modifier.heightIn(4.dp))
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_leasing_permitted_periods),
            value = locationData.allowedLeasingPeriods,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_leasing_mandatory_accessories),
            value = locationData.mandatoryAccessories,
            isDivider = true
        )
        Text(
            text = stringResource(id = R.string.bike_pass_leasing_able_accessories),
            style = MDRTheme.typography.medium,
            fontSize = 16.sp,
            color = MDRTheme.colors.secondaryText
        )
        AllowedAccessoriesMessage(locationData.leasingAllowedAccessories.title, locationData.leasingAllowedAccessories.documentLabel, locationData.leasingAllowedAccessories.documentLink)
        Spacer(Modifier.height(16.dp))
    }
}


@Composable
private fun MyData(customerState: CustomerDataVM, onEvent: (BikePassEvent) -> Unit, rootNavController: NavController) {
    Spacer(Modifier.height(24.dp))
    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .clickable(enabled = false, onClick = {})
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())) {
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_creation_date),
            value = customerState.createdAt,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_company_bike_users),
            value = customerState.shippingAddress,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_phone),
            value = customerState.shippingPhone,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_email),
            value = customerState.shippingEmail,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_contact_person),
            value = customerState.contactPerson,
            isDivider = true
        )
        HorizontalTextBlock(
            title = stringResource(id = R.string.bike_pass_customer_hotline),
            value = customerState.customerHotline,
            isDivider = false
        )
        Spacer(Modifier.weight(1f))
        SecondaryButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.bike_pass_reset_password)
        ) { onEvent(BikePassEvent.OnResetPasswordBtnClicked(rootNavController)) }
        HorizontalDivider(
            thickness = 1.dp,
            color = MDRTheme.colors.secondaryText,
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    bottom = 10.dp
                )
                .fillMaxWidth()
        )
        Row {
            PrimaryButton(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(0.7f),
                text = stringResource(id = R.string.bike_pass_contact)
            ) { onEvent(BikePassEvent.OnSupportBtnClicked) }
            PrimaryButton(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(0.3f),
                text = stringResource(id = R.string.bike_pass_faq)
            ) { onEvent(BikePassEvent.OnFaqBtnClicked) }
        }
        Spacer(Modifier.height(13.dp))
    }
}

@Composable
fun AllowedAccessoriesMessage(message: String, label: String, url: String) {
    val context = LocalContext.current
    val annotatedMessage = buildAnnotatedString {
        withStyle(SpanStyle(color = MDRTheme.colors.titleText, fontSize = 16.sp, fontWeight = FontWeight.Medium)) {
            append(message)
        }
        append(" ")
        withStyle(SpanStyle(color = MDRTheme.colors.titleText, fontSize = 16.sp, fontWeight = FontWeight.Medium, textDecoration = TextDecoration.Underline)) {
            append(label)
        }
        val startIndex = message.length+1
        val endIndex = startIndex + label.length
        if (startIndex >= 0) {
            addStringAnnotation(tag = "URL", annotation = "https://mdr.ado.systems/$url", start = startIndex, end = endIndex)
        }
    }
    ClickableText(
        text = annotatedMessage,
        onClick = { offset ->
            annotatedMessage.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                    context.startActivity(intent)
                }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun BikePassContentPreview() {
    MDRTheme {
        BikePassContent(rootNavController = rememberNavController(), state = BikePassState()) {}
    }
}