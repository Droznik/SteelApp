package com.mdrapp.de.ui.home.myOffers.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.home.myOffers.dialogs.DeleteOfferDialog
import com.mdrapp.de.ui.home.myOffers.list.model.OfferListItemVM
import com.mdrapp.de.ui.home.myOffers.list.view.OfferListItem
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.TitleTopBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Composable
fun OfferListFragment(navController: NavController, vm: OfferListViewModel) {
    BaseFragment(vm, navController) {
        val state by vm.state.collectAsState()
        val onEvent = remember { vm::onEvent }

        OfferListContent(state, onEvent)
    }
}

@Composable
private fun OfferListContent(
    state: OfferListState,
    onEvent: (OfferListEvent) -> Unit
) {
    var offerForDelete by remember { mutableStateOf<OfferListItemVM?>(null) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TitleTopBar(
            title = stringResource(R.string.menu_my_offers),
            count = state.count,
//            actionText = stringResource(R.string.delete_all),
//            onActionClick = { onEvent(OfferListEvent.OnAllOffersDelete) }
        )
        OfferList(
            offers = state.offers,
            onOfferClick = { onEvent(OfferListEvent.OnOfferClick(it)) },
            onOfferDelete = { offerForDelete = it }
        )

        offerForDelete?.let { offer ->
            DeleteOfferDialog(
                onDismissRequest = { offerForDelete = null },
                onConfirm = { onEvent(OfferListEvent.OnOfferDelete(offer.id)) }
            )
        }
    }
}

@Composable
private fun ColumnScope.OfferList(
    offers: ImmutableList<OfferListItemVM>,
    onOfferClick: (OfferListItemVM) -> Unit,
    onOfferDelete: (OfferListItemVM) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        contentPadding = PaddingValues(bottom = 36.dp)
    ) {
        items(offers) { item ->
            OfferListItem(
                item = item,
                onClick = onOfferClick,
                onDelete = onOfferDelete
            )
        }
    }
}

@Preview
@Composable
private fun OfferListPreview() {
    MDRTheme {
        OfferListContent(
            state = OfferListState(
                offers = persistentListOf(
                    OfferListItemVM(
                        id = 1,
                        offerValidUntil = "30.09.24",
                        model = "Model",
                        brand = "Brand",
                        totalPrice = 1500.0,
                        leasingRate = 38.0,
                        dealer = "Test",
                    ),
                    OfferListItemVM(
                        id = 2,
                        offerValidUntil = "30.09.24",
                        model = "Model",
                        brand = "Brand",
                        totalPrice = 1500.0,
                        leasingRate = 38.0,
                        dealer = "Test",
                    )
                ),
                count = 2
            ),
            onEvent = {}
        )
    }
}