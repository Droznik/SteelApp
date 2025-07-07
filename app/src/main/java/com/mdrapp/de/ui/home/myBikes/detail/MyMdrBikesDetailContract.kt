package com.mdrapp.de.ui.home.myBikes.detail

import android.net.Uri
import com.mdrapp.de.domain.model.MdrOrderDetailDM
import com.mdrapp.de.ui.home.myBikes.MyMdrBikesType


data class MyMdrBikesDetailState(
    val type: MyMdrBikesType,
    val orderNumber: String,
    val commonInfo: MdrOrderDetailCommonInfoVM = MdrOrderDetailCommonInfoVM(),
    val bikeInfo: MdrOrderBikeVM = MdrOrderBikeVM(),
    val accessories: List<MdrOrderAccessoryVM> = emptyList(),
    val prices: MdrOrderPricesVM = MdrOrderPricesVM(),
    val dealer: MdrOrderDealerVM = MdrOrderDealerVM(),
    val calculation: MdrOrderCalculationVM = MdrOrderCalculationVM(),
    val orderInfo: MdrOrderInfoVM = MdrOrderInfoVM(),
    val docs: List<MdrOrderDocVM> = emptyList(),
    val allowedToUpload: Boolean = false,
    val trackingStatuses: List<MdrOrderTrackingStatusVM> = emptyList(),
    val isCommonBlockExpanded: Boolean = true,
    val isDocumentBlockExpanded: Boolean = false,
    val isStatusBlockExpanded: Boolean = false,
)

sealed interface MyMdrBikesDetailEvent {
    data object Back : MyMdrBikesDetailEvent
    data class OnCommonBlockClick(val isExpanded: Boolean) : MyMdrBikesDetailEvent
    data class OnDocumentBlockClick(val isExpanded: Boolean) : MyMdrBikesDetailEvent
    data class OnStatusBlockClick(val isExpanded: Boolean) : MyMdrBikesDetailEvent
    data class GetDocumentUrl(val id: Long, val onResult: (String) -> Unit) : MyMdrBikesDetailEvent
    data class UploadDocument(val uri: Uri) : MyMdrBikesDetailEvent
    data object ShowFAQ : MyMdrBikesDetailEvent
}

fun MyMdrBikesDetailState.fromMdrOrderDetailDM(detail: MdrOrderDetailDM) = this.copy(
    commonInfo = detail.toCommonInfoVM(),
    bikeInfo = detail.bike?.toVM() ?: MdrOrderBikeVM(),
    accessories = detail.accessories?.map { it.toVM() }.orEmpty(),
    prices = detail.prices?.toVM() ?: MdrOrderPricesVM(),
    dealer = detail.dealer?.toVM() ?: MdrOrderDealerVM(),
    calculation = detail.calculation?.toVM() ?: MdrOrderCalculationVM(),
    orderInfo = detail.order?.toVM() ?: MdrOrderInfoVM(),
    docs = detail.docs?.map { it.toVM() }.orEmpty(),
    allowedToUpload = detail.allowedToUpload ?: false,
    trackingStatuses = detail.trackingStatuses?.map { it.toVM() }.orEmpty(),
)