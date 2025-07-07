package com.mdrapp.de.ui.home.purchase.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.home.purchase.order.model.EditableAccessoryVM
import com.mdrapp.de.ui.home.purchase.order.view.EditableAccessoryItem
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.BackTopBar
import com.mdrapp.de.ui.views.HorizontalTextBlock
import com.mdrapp.de.ui.views.PrimaryButtonTopLine
import com.mdrapp.de.ui.views.PrimaryContentButton
import com.mdrapp.de.ui.views.PrimaryDateField
import com.mdrapp.de.ui.views.PrimaryOutlinedTextField
import com.mdrapp.de.ui.views.PrimaryTextFieldPlaceholder
import com.mdrapp.de.ui.views.TextBlockDivider
import com.mdrapp.de.ui.views.TextSelector
import com.mdrapp.de.ui.views.VerticalTextBlock
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Composable
fun PurchaseOrderFragment(navController: NavController, vm: PurchaseOrderViewModel) {
    BaseFragment(vm, navController) {
        val state by vm.state.collectAsState()
        val onEvent = remember { vm::onEvent }

        PurchaseOrderContent(state, onEvent)
    }
}

@Composable
private fun PurchaseOrderContent(
    state: PurchaseOrderState,
    onEvent: (PurchaseOrderEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 13.dp)
    ) {
        BackTopBar(stringResource(R.string.purchase_top_title)) { onEvent(PurchaseOrderEvent.Back) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 25.dp, horizontal = 16.dp)
        ) {
            Title()
            PurchaseSpacer(25)
            VerticalTextBlock(stringResource(R.string.purchase_order_cb_user), state.email)
            PurchaseSpacer(25)
            HorizontalTextBlock(stringResource(R.string.first_name), state.firstName)
            TextBlockDivider()
            HorizontalTextBlock(stringResource(R.string.last_name), state.lastName)
            TextBlockDivider()
            HorizontalTextBlock(stringResource(R.string.gender), state.gender)
            PurchaseSpacer(32)
            TextSelector(
                title = stringResource(R.string.purchase_choose_creditors),
                items = state.creditorSelector,
                value = state.creditor,
                isRequired = true,
                searchSize = 1,
                converter = { it.name },
                onValueSelect = { onEvent(PurchaseOrderEvent.OnCreditorSelected(it)) }
            )
            PurchaseSpacer()
            TextSelector(
                title = stringResource(R.string.purchase_bike_type),
                items = state.bikeTypeSelector,
                value = state.bikeType,
                isRequired = true,
                onValueSelect = { onEvent(PurchaseOrderEvent.OnBikeTypeSelected(it)) }
            )
            PurchaseSpacer()
            TextSelector(
                title = stringResource(R.string.purchase_frame_type),
                items = state.frameTypeSelector,
                value = state.frameType,
                isRequired = true,
                onValueSelect = { onEvent(PurchaseOrderEvent.OnFrameTypeSelected(it)) }
            )
            PurchaseSpacer()
            PrimaryOutlinedTextField(
                value = state.bikeManufacturer,
                singleLine = true,
                readOnly = state.fromOffer,
                label = { PrimaryTextFieldPlaceholder(R.string.purchase_bike_brand, isRequired = true) },
                onValueChange = { onEvent(PurchaseOrderEvent.OnBikeManufacturerChanged(it)) }
            )
            PurchaseSpacer()
            PrimaryOutlinedTextField(
                value = state.bikeName,
                singleLine = true,
                readOnly = state.fromOffer,
                label = { PrimaryTextFieldPlaceholder(R.string.purchase_bike_model, isRequired = true) },
                onValueChange = { onEvent(PurchaseOrderEvent.OnBikeNameChanged(it)) }
            )
            PurchaseSpacer()
            PrimaryOutlinedTextField(
                value = state.bikeColor,
                singleLine = true,
                readOnly = state.fromOffer,
                label = { PrimaryTextFieldPlaceholder(R.string.purchase_bike_color, isRequired = true) },
                onValueChange = { onEvent(PurchaseOrderEvent.OnBikeColorChanged(it)) }
            )
            PurchaseSpacer()
            PrimaryOutlinedTextField(
                value = state.bikeGroup,
                singleLine = true,
                readOnly = state.fromOffer,
                label = { PrimaryTextFieldPlaceholder(R.string.purchase_bike_category, isRequired = true) },
                onValueChange = { onEvent(PurchaseOrderEvent.OnBikeGroupChanged(it)) }
            )
            PurchaseSpacer()
            PrimaryOutlinedTextField(
                value = state.bikeFrameHeight,
                singleLine = true,
                readOnly = state.fromOffer,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                label = { PrimaryTextFieldPlaceholder(R.string.purchase_frame_height, isRequired = true) },
                onValueChange = { onEvent(PurchaseOrderEvent.OnBikeFrameHeightChanged(it)) }
            )
            PurchaseSpacer()
            PrimaryOutlinedTextField(
                value = state.bikeFrameNumber,
                singleLine = true,
                readOnly = state.fromOffer,
                label = { PrimaryTextFieldPlaceholder(R.string.purchase_frame_number) },
                onValueChange = { onEvent(PurchaseOrderEvent.OnBikeFrameNumberChanged(it)) }
            )
            PurchaseSpacer()
            val context = LocalContext.current
            TextSelector(
                title = stringResource(R.string.purchase_lager_status),
                items = state.lagerStatusSelector,
                value = state.lagerStatus,
                converter = { (context.resources.getString(it.titleId)) },
                onValueSelect = { onEvent(PurchaseOrderEvent.OnLagerStatusSelected(it)) }
            )
            PurchaseSpacer()
            PrimaryDateField(
                label = R.string.purchase_date,
                date = state.date,
                isRequired = true,
                readOnly = state.fromOffer,
                onDatePicked = { onEvent(PurchaseOrderEvent.OnDateChanged(it)) }
            )
            PurchaseSpacer()
            TextSelector(
                title = stringResource(R.string.purchase_contract_term),
                items = state.durationSelector,
                value = state.duration,
                isRequired = true,
                suffix = stringResource(R.string.months),
                onValueSelect = { onEvent(PurchaseOrderEvent.OnDurationSelected(it)) }
            )
            PurchaseSpacer()
            PrimaryOutlinedTextField(
                value = state.bikeUvp,
                singleLine = true,
                readOnly = state.fromOffer,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { PrimaryTextFieldPlaceholder(R.string.purchase_bike_uvp, isRequired = true) },
                onValueChange = { onEvent(PurchaseOrderEvent.OnBikeUvpChanged(it)) }
            )
            PurchaseSpacer()
            PrimaryOutlinedTextField(
                value = state.bikePrice,
                singleLine = true,
                readOnly = state.fromOffer,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { PrimaryTextFieldPlaceholder(R.string.purchase_bike_price, isRequired = true) },
                onValueChange = { onEvent(PurchaseOrderEvent.OnBikePriceChanged(it)) }
            )
            if(state.mdrAccessories.isNotEmpty()) {
                MDRAccessories(state.mdrAccessories)
            }
            if (state.notAllowedAccessories.isNotEmpty()) {
                NotAllowedAccessories(state.notAllowedAccessories)
            }
            if (state.requiredAccessories.isNotEmpty()) {
                RequiredAccessories(state.requiredAccessories, onEvent)
            }
            when {
                state.fromOffer -> {
                    if (state.isLeasingAccessories) {
                        LeasingAccessories(
                            isLeasingAccessories = true,
                            accessories = state.extraAccessories,
                            onEvent = onEvent,
                            fromOffer = true
                        )
                    }
                }
                else -> LeasingAccessories(state.isLeasingAccessories, state.extraAccessories, onEvent)
            }
            PurchaseSpacer(38)
            if (state.showEmployeeBudget) {
                PrimaryOutlinedTextField(
                    value = state.employeeBudget,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { PrimaryTextFieldPlaceholder(R.string.purchase_employee_budget,) },
                    onValueChange = { onEvent(PurchaseOrderEvent.OneEmployeeBudgetChanged(it)) }
                )
                PurchaseSpacer()
            }
            TextSelector(
                title = stringResource(R.string.purchase_service_packages_title),
                items = state.servicePackageSelector,
                value = state.servicePackage,
                isRequired = true,
                converter = { it.displayTitle },
                onValueSelect = { onEvent(PurchaseOrderEvent.OnServicePackageSelected(it)) }
            )
            PurchaseSpacer(26)
            HorizontalTextBlock(
                title = stringResource(R.string.purchase_total_price),
                value = stringResource(R.string.price_euro, state.totalPrice)
            )
            TextBlockDivider()
            HorizontalTextBlock(
                title = stringResource(R.string.purchase_insurance_rate),
                value = stringResource(R.string.price_euro, state.insuranceRate)
            )
            TextBlockDivider()
            HorizontalTextBlock(
                title = stringResource(R.string.purchase_leasing_rate),
                value = stringResource(R.string.price_euro, state.leasingRate)
            )
            PurchaseSpacer(36)
        }
        PrimaryButtonTopLine(
            text = stringResource(R.string.purchase_order_create_btn),
            enabled = state.showButtonNext,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            onEvent(PurchaseOrderEvent.Order)
        }
    }
}

@Composable
private fun Title() {
    Text(
        text = "${stringResource(R.string.purchase_top_title)}.",
        style = MDRTheme.typography.title,
        color = MDRTheme.colors.titleText,
        modifier = Modifier.padding(horizontal = 4.dp)
    )
}

@Composable
private fun AccessoriesTitle(title: String, isLeasingAccessories: Boolean? = null) {
    val resultTitle = when(isLeasingAccessories) {
        true -> "$title ${stringResource(R.string.yes)}"
        false -> "$title ${stringResource(R.string.no)}"
        else -> title
    }
    PurchaseSpacer(38)
    Text(
        text = resultTitle,
        style = MDRTheme.typography.semiBold,
        fontSize = 18.sp,
        color = MDRTheme.colors.titleText,
    )
    PurchaseSpacer(12)
}

@Composable
private fun MDRAccessories(accessories: String) {
    AccessoriesTitle(stringResource(R.string.purchase_mdr_accessory))
    HorizontalTextBlock(stringResource(R.string.accessory), accessories)
    TextBlockDivider()
}

@Composable
private fun NotAllowedAccessories(accessories: String) {
    AccessoriesTitle(stringResource(R.string.purchase_not_allowed_accessories))
    Text(
        text = accessories,
        style = MDRTheme.typography.regular,
        fontSize = 14.sp,
        color = MDRTheme.colors.primaryText,
    )
}

@Composable
private fun RequiredAccessories(
    accessories: ImmutableList<EditableAccessoryVM>,
    onEvent: (PurchaseOrderEvent) -> Unit
) {
    val last = accessories.size - 1

    AccessoriesTitle(stringResource(R.string.purchase_required_accessories))
    accessories.forEachIndexed { index, item ->
        HorizontalTextBlock(stringResource(R.string.accessory), item.displayCategory)
        TextBlockDivider()
        PurchaseSpacer(8)
        EditableAccessoryItem(
            item = item,
            onItemCalcFieldChanged = { onEvent(PurchaseOrderEvent.OnItemCalcFieldChanged) },
            onItemRequiredFieldChanged = { onEvent(PurchaseOrderEvent.OnItemRequiredFieldChanged) }
        )
        if (index < last) {
            PurchaseSpacer(32)
        }
    }
}

@Composable
private fun LeasingAccessories(
    isLeasingAccessories: Boolean,
    accessories: ImmutableList<EditableAccessoryVM>,
    onEvent: (PurchaseOrderEvent) -> Unit,
    fromOffer: Boolean = false,
) {
    val last = accessories.size - 1

    AccessoriesTitle(stringResource(R.string.purchase_leasing_accessories), isLeasingAccessories)
    accessories.forEachIndexed { index, item ->
        EditableAccessoryItem(
            item = item,
            onItemCalcFieldChanged = { onEvent(PurchaseOrderEvent.OnItemCalcFieldChanged) },
            onItemRequiredFieldChanged = { onEvent(PurchaseOrderEvent.OnItemRequiredFieldChanged) },
            onDelete = { onEvent(PurchaseOrderEvent.OnLeasingItemDelete(index)) }
        )
        if (index < last) {
            PurchaseSpacer(32)
        }
    }
    if (accessories.isNotEmpty()) {
        PurchaseSpacer()
    }
    if (!fromOffer) {
        PrimaryContentButton(
            enabled = isLeasingAccessories,
            content = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_plus_large),
                    contentDescription = null,
                    tint = MDRTheme.colors.lightText
                )
            }
        ) { onEvent(PurchaseOrderEvent.OnAddLeasingAccessory) }
    }
}


@Composable
private fun PurchaseSpacer(height: Int = 16) {
    Spacer(modifier = Modifier.height(height = height.dp))
}

@Preview(heightDp = 2000)
@Composable
private fun PurchaseOrderPreview() {
    MDRTheme {
        val state = PurchaseOrderState(
            firstName = "Max",
            lastName = "Musterman",
            email = "kurademo-mdrnutzer@baronmobil.com",
            gender = "Herr",
            mdrAccessories = "Helm",
            notAllowedAccessories = "Helm, Schloss",
            requiredAccessories = persistentListOf(
//                EditableAccessoryVM(
//                    id = 1,
//                    isRequiredAccessory = true,
//                    initialUvp = 30.0,
//                    displayCategory = "Helm"
//                )
            ),
            showEmployeeBudget = true
        )

        PurchaseOrderContent(state) {}
    }
}