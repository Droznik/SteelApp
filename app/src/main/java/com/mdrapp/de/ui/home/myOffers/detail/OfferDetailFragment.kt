package com.mdrapp.de.ui.home.myOffers.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.home.myOffers.detail.model.OfferDetailAccessoryInfoVM
import com.mdrapp.de.ui.home.myOffers.detail.model.OfferDetailAccessoryVM
import com.mdrapp.de.ui.home.myOffers.detail.model.OfferDetailBikeVM
import com.mdrapp.de.ui.home.myOffers.detail.model.OfferDetailCalculationVM
import com.mdrapp.de.ui.home.myOffers.detail.model.OfferDetailDealerVM
import com.mdrapp.de.ui.home.myOffers.detail.view.OfferDetailAccessoryItem
import com.mdrapp.de.ui.home.myOffers.dialogs.DeleteOfferDialog
import com.mdrapp.de.ui.home.myOffers.list.OfferListEvent
import com.mdrapp.de.ui.home.myOffers.list.OfferListViewModel
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.BackTopBar
import com.mdrapp.de.ui.views.HorizontalTextBlock
import com.mdrapp.de.ui.views.PrimaryButton
import kotlinx.collections.immutable.persistentListOf


@Composable
fun OfferDetailFragment(
    navController: NavController,
    offerDetailVM: OfferDetailViewModel,
    offerListVM: OfferListViewModel
) {
    BaseFragment(offerDetailVM, navController) {
        var showDeleteDialog by remember { mutableStateOf(false) }
        val state by offerDetailVM.state.collectAsState()
        val onEvent = remember { offerDetailVM::onEvent }

        if (showDeleteDialog) DeleteOfferDialog(
            onDismissRequest = { showDeleteDialog = false },
            onConfirm = {
                offerListVM.onEvent(OfferListEvent.OnOfferDelete(offerDetailVM.offerId))
                navController.navigateUp()
            }
        )

        OfferDetailContent(state, onDelete = { showDeleteDialog = true }, onEvent)
    }
}

@Composable
private fun OfferDetailContent(
    state: OfferDetailState,
    onDelete: () -> Unit,
    onEvent: (OfferDetailEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        BackTopBar(
            title = stringResource(R.string.menu_my_offers),
            subTitle = state.bike.brand
        ) { onEvent(OfferDetailEvent.Back) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Title(state.bike.brand)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 8.dp)
            ){
                HorizontalTextBlock(
                    title = stringResource(R.string.total_price),
                    value = stringResource(R.string.price_euro, state.purchaseTotalPrice)
                )
                Spacer(modifier = Modifier.height(6.dp))
                HorizontalTextBlock(
                    title = stringResource(R.string.leasing_rate),
                    value = stringResource(R.string.price_euro, state.leasingRate)
                )
                Spacer(modifier = Modifier.height(38.dp))
                BikeBlock(state.bike)
                Spacer(modifier = Modifier.height(38.dp))
                AccessoryBlock(state.accessoryInfo)
                CalculationBlock(state.calculation)
                Spacer(modifier = Modifier.height(26.dp))
                DealerBlock(state.dealer)
                Spacer(modifier = Modifier.height(46.dp))
                TermBlock(state.offerValidUntil)
                Spacer(modifier = Modifier.height(36.dp))
            }
        }
        ActionBlock(onDelete, onEvent)
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
fun OrderSubTitle(subTitle: String) {
    Text(
        text = subTitle,
        style = MDRTheme.typography.semiBold,
        fontSize = 19.sp,
        color = MDRTheme.colors.titleText,
    )
}

@Composable
private fun BikeBlock(bike: OfferDetailBikeVM) {
    OrderSubTitle(stringResource(R.string.bike))
    Spacer(modifier = Modifier.height(20.dp))
    HorizontalTextBlock(stringResource(R.string.model), bike.model, true)
    HorizontalTextBlock(stringResource(R.string.type), bike.type, true)
    HorizontalTextBlock(stringResource(R.string.frame_type), bike.frameType, true)
    HorizontalTextBlock(stringResource(R.string.category), bike.category, true)
    HorizontalTextBlock(stringResource(R.string.frame_size), bike.frameSize, true)
    HorizontalTextBlock(stringResource(R.string.color), bike.color)
}

@Composable
private fun AccessoryBlock(info: OfferDetailAccessoryInfoVM) {
    if (info.accessories.isNotEmpty()) {
        OrderSubTitle(stringResource(R.string.accessory))
        info.accessories.forEach {
            Spacer(modifier = Modifier.height(20.dp))
            OfferDetailAccessoryItem(it)
        }
        Spacer(modifier = Modifier.height(38.dp))
    }
    OrderSubTitle(stringResource(R.string.total_prices))
    Spacer(modifier = Modifier.height(20.dp))
    HorizontalTextBlock(
        title = stringResource(R.string.total_price_net),
        value = stringResource(R.string.price_euro, info.totalPriceNet),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.total_price_gross),
        value = stringResource(R.string.price_euro, info.totalPriceGross),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.gross_list_price_uvp),
        value = stringResource(R.string.price_euro, info.uvp),
    )
    Spacer(modifier = Modifier.height(38.dp))
}

@Composable
private fun CalculationBlock(info: OfferDetailCalculationVM) {
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
        value = stringResource(R.string.price_euro, info.lessEmployerContribution),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.withheld_from_gross_salary),
        value = stringResource(R.string.price_euro, info.withheldFromGrossSalary),
        isDivider = true
    )
    HorizontalTextBlock(
        title = stringResource(R.string.pecuniary_advantage),
        value = stringResource(R.string.price_euro, info.pecuniaryAdvantage)
    )
}

@Composable
private fun DealerBlock(dealer: OfferDetailDealerVM) {
    Text(
        text = stringResource(R.string.dealer),
        style = MDRTheme.typography.medium,
        fontSize = 16.sp,
        color = MDRTheme.colors.primaryText
    )
    Spacer(modifier = Modifier.height(11.dp))
    Text(
        text = buildString {
            if (dealer.name.isNotBlank()) append(dealer.name).append("\n")
            if (dealer.shippingAddress.isNotBlank()) append(dealer.shippingAddress).append("\n")
            if (dealer.shippingPostcode.isNotBlank()) append(dealer.shippingPostcode).append(" ")
            if (dealer.shippingCity.isNotBlank()) append(dealer.shippingCity).append("\n")
            else append("\n")
            if (dealer.shippingPhone.isNotBlank()) append(dealer.shippingPhone).append("\n")
            if (dealer.website.isNotBlank()) append(dealer.website).append("\n")
        },
        style = MDRTheme.typography.regular,
        fontSize = 15.sp,
        color = MDRTheme.colors.secondaryText
    )
}

@Composable
private fun TermBlock(term: String) {
    Text(
        text = stringResource(R.string.offer_item_valid_until),
        style = MDRTheme.typography.regular,
        fontSize = 15.sp,
        color = MDRTheme.colors.secondaryText,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(5.dp))
    Text(
        text = term,
        style = MDRTheme.typography.medium,
        fontSize = 16.sp,
        color = MDRTheme.colors.titleText,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun ActionBlock(onDelete: () -> Unit, onEvent: (OfferDetailEvent) -> Unit) {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        color = MDRTheme.colors.secondaryText
    )
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 11.dp, bottom = 13.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable { onDelete() }
        )
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            text = stringResource(R.string.order_company_bike),
            modifier = Modifier.width(249.dp)
        ) { onEvent(OfferDetailEvent.ShowOrderForm) }
    }
}

@Preview(heightDp = 1600)
@Composable
private fun OfferDetailPreview() {
    MDRTheme {
        OfferDetailContent(
            state = OfferDetailState(
                offerValidUntil = "30.09.2024",
                bike = OfferDetailBikeVM(brand = "Bianchi"),
                accessoryInfo = OfferDetailAccessoryInfoVM(
                    accessories = persistentListOf(
                        OfferDetailAccessoryVM(
                            title = "",
                            quantity = 1,
                            netSellingPrice = 0.0,
                            grossSalesPrice = 0.0,
                            salesPriceGrossIncl = "",
                            uvp = 0.0,
                        ),
                        OfferDetailAccessoryVM(
                            title = "",
                            quantity = 1,
                            netSellingPrice = 0.0,
                            grossSalesPrice = 0.0,
                            salesPriceGrossIncl = "",
                            uvp = 0.0,
                        )
                    )
                ),
                dealer = OfferDetailDealerVM(
                    name = "name",
                    shippingAddress = "shippingAddress",
                    shippingPostcode = "shippingPostcode",
                    shippingCity = "shippingCity",
                    shippingPhone = "shippingPhone",
                    website = "website",
                )
            ),
            {},
            {}
        )
    }
}