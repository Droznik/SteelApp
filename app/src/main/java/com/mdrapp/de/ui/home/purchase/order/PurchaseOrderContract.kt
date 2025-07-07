package com.mdrapp.de.ui.home.purchase.order

import androidx.core.text.HtmlCompat
import com.mdrapp.de.domain.model.OfferOrderInfoDM
import com.mdrapp.de.domain.model.PurchaseInfoDM
import com.mdrapp.de.domain.model.SetAccessoryDM
import com.mdrapp.de.domain.model.SetPurchaseDM
import com.mdrapp.de.ui.home.purchase.order.model.CreditorVM
import com.mdrapp.de.ui.home.purchase.order.model.EditableAccessoryVM
import com.mdrapp.de.ui.home.purchase.order.model.LagerStatusVM
import com.mdrapp.de.ui.home.purchase.order.model.ServicePackageVM
import com.mdrapp.de.ui.home.purchase.order.model.lagerStatuses
import com.mdrapp.de.ui.home.purchase.order.model.toVM
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList


data class PurchaseOrderState(
    val fromOffer: Boolean = false,
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val gender: String = "",
    val bikeTypeSelector: ImmutableList<String> = persistentListOf(),
    val bikeType: String = "",
    val frameTypeSelector: ImmutableList<String> = persistentListOf(),
    val frameType: String = "",
    val durationSelector: ImmutableList<Int> = persistentListOf(),
    val duration: Int? = null,
    val isLeasingAccessories: Boolean = false,
    val mdrAccessories: String = "",
    val requiredAccessories: ImmutableList<EditableAccessoryVM> = persistentListOf(),
    val extraAccessories: ImmutableList<EditableAccessoryVM> = persistentListOf(),
    val notAllowedAccessories: String = "",
    val showEmployeeBudget: Boolean = false,
    val servicePackageSelector: ImmutableList<ServicePackageVM> = persistentListOf(),
    val servicePackage: ServicePackageVM? = null,
    val creditorSelector: ImmutableList<CreditorVM> = persistentListOf(),
    val creditor: CreditorVM? = null,
    val lagerStatus: LagerStatusVM? = null,
    val lagerStatusSelector: ImmutableList<LagerStatusVM> = persistentListOf(),

    val bikeManufacturer: String = "",
    val bikeName: String = "",
    val bikeColor: String = "",
    val bikeFrameHeight: String = "",
    val bikeUvp: String = "",
    val bikePrice: String = "",
    val bikeGroup: String = "",
    val bikeFrameNumber: String = "",
    val date: String = "",
    val employeeBudget: String = "",

    val totalPrice: Double = 0.0,
    val insuranceRate: Double = 0.0,
    val leasingRate: Double = 0.0,

    val showButtonNext: Boolean = false
)

sealed interface PurchaseOrderEvent {
    data object Back : PurchaseOrderEvent
    data object Order : PurchaseOrderEvent

    data class OnBikeManufacturerChanged(val value: String) : PurchaseOrderEvent
    data class OnBikeNameChanged(val value: String) : PurchaseOrderEvent
    data class OnBikeColorChanged(val value: String) : PurchaseOrderEvent
    data class OnBikeFrameHeightChanged(val value: String) : PurchaseOrderEvent
    data class OnBikeUvpChanged(val value: String) : PurchaseOrderEvent
    data class OnBikePriceChanged(val value: String) : PurchaseOrderEvent
    data class OnBikeGroupChanged(val value: String) : PurchaseOrderEvent
    data class OnBikeFrameNumberChanged(val value: String) : PurchaseOrderEvent
    data class OnDateChanged(val value: Long) : PurchaseOrderEvent
    data class OneEmployeeBudgetChanged(val value: String) : PurchaseOrderEvent

    data class OnBikeTypeSelected(val value: String) : PurchaseOrderEvent
    data class OnFrameTypeSelected(val value: String) : PurchaseOrderEvent
    data class OnDurationSelected(val value: Int) : PurchaseOrderEvent
    data class OnServicePackageSelected(val value: ServicePackageVM) : PurchaseOrderEvent
    data class OnCreditorSelected(val value: CreditorVM?) : PurchaseOrderEvent
    data class OnLagerStatusSelected(val value: LagerStatusVM) : PurchaseOrderEvent

    data object OnItemCalcFieldChanged : PurchaseOrderEvent
    data object OnItemRequiredFieldChanged : PurchaseOrderEvent
    data object OnAddLeasingAccessory : PurchaseOrderEvent
    data class OnLeasingItemDelete(val index: Int) : PurchaseOrderEvent
}

fun PurchaseOrderState.fromPurchaseInfoDM(info: PurchaseInfoDM) = this.copy(
    firstName = info.firstName.orEmpty(),
    lastName = info.lastLame.orEmpty(),
    email = info.email.orEmpty(),
    gender = info.gender.orEmpty(),
    bikeTypeSelector = info.bikeTypes.orEmpty().toPersistentList(),
    frameTypeSelector = info.frameTypes.orEmpty().toPersistentList(),
    durationSelector = info.duration.orEmpty().toPersistentList(),
    duration = info.duration?.minOrNull(),
    isLeasingAccessories = info.isLeasingAccessories ?: false,
    mdrAccessories = info.mdrAccessories.orEmpty().joinToString(", "),
    requiredAccessories = info.requiredAccessories.orEmpty().mapIndexed { index, item -> item.toVM(index) }.toPersistentList(),
    notAllowedAccessories = HtmlCompat.fromHtml(info.notAllowedAccessories.orEmpty(), HtmlCompat.FROM_HTML_MODE_COMPACT).toString(),
    showEmployeeBudget = info.employeeBudget ?: false,
    servicePackageSelector = info.servicePackages.orEmpty().map { it.toVM() }.toPersistentList(),
    servicePackage = if (servicePackageSelector.size == 1) servicePackageSelector.first() else null,
    creditorSelector = info.creditors.orEmpty().map { it.toVM() }.toPersistentList(),
    lagerStatusSelector = lagerStatuses
)

fun PurchaseOrderState.fromOfferOrderInfoDM(info: OfferOrderInfoDM) = this.copy(
    firstName = info.firstName.orEmpty(),
    lastName = info.lastName.orEmpty(),
    email = info.email.orEmpty(),
    gender = info.gender.orEmpty(),
    bikeType = info.bikeType.orEmpty(),
    frameType = info.frameType.orEmpty(),
    durationSelector = info.duration.orEmpty().toPersistentList(),
    duration = info.duration?.minOrNull(),
    mdrAccessories = info.mdrAccessories.orEmpty().joinToString(", "),
    isLeasingAccessories = !(info.leasingAccessories.isNullOrEmpty()),
    requiredAccessories = info.accessories.orEmpty()
        .mapIndexed { index, item -> item.toVM(index, true) }
        .toPersistentList(),
    extraAccessories = info.leasingAccessories.orEmpty()
        .mapIndexed { index, item -> item.toVM(index) }
        .toPersistentList(),
    notAllowedAccessories = "",
    showEmployeeBudget = info.isEmployeeBudget ?: false,
    servicePackageSelector = info.servicePackages.orEmpty()
        .map { it.toVM() }.toPersistentList(),
    servicePackage = null,
    creditor = info.creditor?.toVM(),
    lagerStatus = lagerStatuses.firstOrNull { it.value == info.lagerStatus },
    bikeManufacturer = info.brand.orEmpty(),
    bikeName = info.model.orEmpty(),
    bikeColor = info.color.orEmpty(),
    bikeFrameHeight = info.frameSize.orEmpty(),
    bikeUvp = info.uvp.toString(),
    bikePrice = info.price.toString(),
    bikeGroup = info.subType.orEmpty(),
    bikeFrameNumber = info.frameNumber.orEmpty(),
    date = info.date.orEmpty(),
    totalPrice = info.totalPurchasePrice ?: 0.0,
    insuranceRate = info.insuranceRate ?: 0.0,
    leasingRate = info.leasingRate ?: 0.0,
)

fun PurchaseOrderState.toDomain(offerId: Long?): SetPurchaseDM = SetPurchaseDM(
    offerId = offerId,
    bikeManufacturer = bikeManufacturer,
    bikeName = bikeName,
    bikeColor = bikeColor,
    bikeType = bikeType,
    bikeFrameHeight = bikeFrameHeight,
    bikeUvp = bikeUvp.toDouble(),
    bikePrice = bikePrice.toDouble(),
    bikeGroup = bikeGroup,
    bikeFrameNumber = bikeFrameNumber.takeIf { it.isNotBlank() },
    frameType = frameType,
    date = date,
    lagerStatus = lagerStatus?.value,
    creditorId = creditor?.id!!,
    accessories = (requiredAccessories + extraAccessories).map {
       SetAccessoryDM(
           name = it.title,
           category = it.category,
           price = it.price.toDouble(),
           priceUvp = it.uvp.toDouble(),
           quantity = it.quantity,
       )
    },
    employeeBudget = employeeBudget.toDoubleOrNull(),
    duration = duration!!,
    servicePackageId = servicePackage?.id!!,
)