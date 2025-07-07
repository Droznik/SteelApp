package com.mdrapp.de.ui.home.purchase.order

import androidx.lifecycle.SavedStateHandle
import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.model.CalculationAccessoryDM
import com.mdrapp.de.domain.model.SetCalculationsDM
import com.mdrapp.de.domain.repository.PurchaseRepository
import com.mdrapp.de.ext.formattedAsDouble
import com.mdrapp.de.ext.toDateString
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.navigation.OFFER_ID_ARG
import com.mdrapp.de.navigation.PopUpToAction
import com.mdrapp.de.navigation.PurchaseGraph
import com.mdrapp.de.ui.home.purchase.order.model.EditableAccessoryVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class PurchaseOrderViewModel @Inject constructor(
    private val purchaseRepo: PurchaseRepository,
    savedStateHandle: SavedStateHandle
) : ContractViewModel<PurchaseOrderState, PurchaseOrderEvent>() {

    private val offerId: Long? = savedStateHandle.get<Long>(OFFER_ID_ARG).takeIf { it != -1L }
    private val fromOffer = offerId != null
    
    private var customerId: Long? = null

    override val initialState = PurchaseOrderState(fromOffer = fromOffer)
    private var isCalculated: Boolean = false

    init {
        loadPurchaseInfo()
    }

    private var calculationJob: Job? = null

    override fun onEvent(event: PurchaseOrderEvent) {
        when(event) {
            PurchaseOrderEvent.Back -> navigate(NavEvent.Back)
            PurchaseOrderEvent.Order -> createOrder()

            is PurchaseOrderEvent.OnBikeColorChanged -> _state.update { it.copy(bikeColor = event.value) }.also { checkNextButton() }
            is PurchaseOrderEvent.OnBikeFrameHeightChanged -> _state.update { it.copy(bikeFrameHeight = event.value) }.also { checkNextButton() }
            is PurchaseOrderEvent.OnBikeFrameNumberChanged -> _state.update { it.copy(bikeFrameNumber = event.value) }
            is PurchaseOrderEvent.OnBikeGroupChanged -> _state.update { it.copy(bikeGroup = event.value) }.also { checkNextButton() }
            is PurchaseOrderEvent.OnBikeManufacturerChanged -> _state.update { it.copy(bikeManufacturer = event.value) }.also { checkNextButton() }
            is PurchaseOrderEvent.OnBikeNameChanged -> _state.update { it.copy(bikeName = event.value) }.also { checkNextButton() }
            is PurchaseOrderEvent.OnBikePriceChanged -> _state.update { it.copy(bikePrice = event.value.formattedAsDouble(it.bikePrice)) }.also { calculate() }
            is PurchaseOrderEvent.OnBikeTypeSelected -> _state.update { it.copy(bikeType = event.value) }.also { calculate() }
            is PurchaseOrderEvent.OnBikeUvpChanged -> _state.update { it.copy(bikeUvp = event.value.formattedAsDouble(it.bikeUvp)) }.also { calculate() }
            is PurchaseOrderEvent.OnCreditorSelected -> _state.update { it.copy(creditor = event.value) }.also { calculate() }
            is PurchaseOrderEvent.OnDateChanged -> _state.update { it.copy(date = event.value.toDateString("yyyy-MM-dd")) }.also { checkNextButton() }
            is PurchaseOrderEvent.OnDurationSelected -> _state.update { it.copy(duration = event.value) }.also { calculate() }
            is PurchaseOrderEvent.OnFrameTypeSelected -> _state.update { it.copy(frameType = event.value) }.also { checkNextButton() }
            is PurchaseOrderEvent.OnLagerStatusSelected -> _state.update { it.copy(lagerStatus = event.value) }
            is PurchaseOrderEvent.OnServicePackageSelected -> _state.update { it.copy(servicePackage = event.value) }.also { checkNextButton() }
            is PurchaseOrderEvent.OneEmployeeBudgetChanged -> _state.update { it.copy(employeeBudget = event.value.formattedAsDouble(it.employeeBudget)) }.also { calculate() }

            PurchaseOrderEvent.OnItemCalcFieldChanged -> calculate()
            PurchaseOrderEvent.OnItemRequiredFieldChanged -> checkNextButton()
            PurchaseOrderEvent.OnAddLeasingAccessory -> addLeasingAccessory()
            is PurchaseOrderEvent.OnLeasingItemDelete -> deleteLeasingAccessory(event.index)
        }
    }

    private fun loadPurchaseInfo() = simpleLaunch {
        if (fromOffer) {
            val purchaseInfo = withContext(Dispatchers.IO) { purchaseRepo.getOfferOrderInfo(offerId!!) }
            customerId = purchaseInfo.customerId
            _state.update { it.fromOfferOrderInfoDM(purchaseInfo) }
            isCalculated = true
        } else {
            val purchaseInfo = withContext(Dispatchers.IO) { purchaseRepo.getPurchaseInfo() }
            customerId = purchaseInfo.customerId
            _state.update { it.fromPurchaseInfoDM(purchaseInfo) }
        }
    }

    private fun createOrder() = simpleLaunch {
        withContext(Dispatchers.IO) { purchaseRepo.setPurchase(state.value.toDomain(offerId)) }
        navigate(
            NavEvent.To(
                route = PurchaseGraph.Result.route,
                popUpTo = PopUpToAction.RouteDestination(HomeNavHost.Home.route, false)
            )
        )
    }

    private fun calculate() {
        isCalculated = false
        calculationJob?.cancel()
        if (state.value.showButtonNext) { _state.update { it.copy(showButtonNext = false) } }

        val accessories = state.value.requiredAccessories + state.value.extraAccessories
        val request = SetCalculationsDM(
            customerId = customerId ?: return,
            creditorId = state.value.creditor?.id ?: return,
            price = state.value.bikePrice.toDoubleOrNull() ?: return,
            bikeType = state.value.bikeType.takeIf { it.isNotEmpty() } ?: return,
            duration = state.value.duration ?: return,
            uvp = state.value.bikeUvp.toDoubleOrNull() ?: return,
            employeeBudget = state.value.employeeBudget.toDoubleOrNull(),
            accessories = accessories.map {
                CalculationAccessoryDM(
                    uvp = it.getUvpDoubleOrNull() ?: return,
                    price = it.price.toDoubleOrNull() ?: return,
                    quantity = it.quantity
                )
            }.takeIf { it.isNotEmpty() },
        )

        calculationJob = simpleLaunch {
            val result = withContext(Dispatchers.IO) { purchaseRepo.calculate(request) }

            isCalculated = true
            _state.update { it.copy(
                totalPrice = result.totalPurchasePrice ?: 0.0,
                insuranceRate = result.insuranceRate ?: 0.0,
                leasingRate = result.leasingRate ?: 0.0,
                showButtonNext = isShowNextButton()
            ) }
        }
    }

    private fun checkNextButton() {
        if (isCalculated) {
            _state.update { it.copy(showButtonNext = isShowNextButton()) }
        }
    }

    private fun isShowNextButton(): Boolean = state.value.let {
        val accessories = it.requiredAccessories + it.extraAccessories

        accessories.forEach { item ->
            if (!item.validated()) return@let false
        }

        when {
            it.bikeManufacturer.isEmpty() ||
            it.bikeName.isEmpty() ||
            it.bikeColor.isEmpty() ||
            it.bikeType.isEmpty() ||
            it.frameType.isEmpty() ||
            it.bikeFrameHeight.isEmpty() ||
            it.bikeUvp.isEmpty() ||
            it.bikePrice.isEmpty() ||
            it.bikeGroup.isEmpty() ||
            it.date.isEmpty() ||
            it.creditor == null ||
            it.servicePackage == null -> false
            else -> true
        }
    }

    private fun addLeasingAccessory() {
        _state.update {
            it.copy(
                extraAccessories = (it.extraAccessories + listOf(EditableAccessoryVM(it.extraAccessories.size)))
                    .toPersistentList(),
                showButtonNext = false
            )
        }
    }

    private fun deleteLeasingAccessory(index: Int) {
        _state.update {
            it.copy(
                extraAccessories = it.extraAccessories
                    .toMutableList()
                    .apply { removeAt(index) }
                    .toPersistentList()
            )
        }
    }
}