package com.mdrapp.de.data.model.remote.purchase

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.CreditorDM
import com.mdrapp.de.domain.model.PurchaseInfoDM
import com.mdrapp.de.domain.model.RequireAccessoryDM
import com.mdrapp.de.domain.model.ServicePackageDM


data class GetOrderResponse(
    @SerializedName("response") val response: OrderResponse? = null
): BaseResponse()

data class OrderResponse(
    @SerializedName("customer") val customer: PurchaseCustomer,
    @SerializedName("bikeTypes") val bikeTypes: List<String>? = null,
    @SerializedName("rahmenart") val frameTypes: List<String>? = null,
    @SerializedName("duration") val duration: List<Int>? = null,
    @SerializedName("accessories") val accessories: PurchaseAccessories? = null,
    @SerializedName("employeeBudget") val employeeBudget: Boolean? = null,
    @SerializedName("servicePackages") val servicePackages: List<ServicePackage>? = null,
    @SerializedName("creditors") val creditors: List<Creditor>? = null,
)

data class PurchaseCustomer(
    @SerializedName("id") val id: Long,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastLame: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("gender") val gender: String? = null,
)

data class PurchaseAccessories(
    @SerializedName("accessoriesList") val requiredAccessories: List<RequireAccessory>? = null,
    @SerializedName("isLeasingAccessories") val isLeasingAccessories: Boolean? = null,
    @SerializedName("mdrAccessories") val mdrAccessories: List<String?>? = null,
    @SerializedName("notAllowedAccessories") val notAllowedAccessories: String? = null,
)

data class RequireAccessory(
    @SerializedName("price") val price: Double? = null,
    @SerializedName("mandatory_accessories") val requiredAccessoryCategory: String? = null
)

data class ServicePackage(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String? = null,
    @SerializedName("price") val price: String? = null
)

data class Creditor(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String? = null
)

fun GetOrderResponse.toDM(): PurchaseInfoDM = PurchaseInfoDM(
    customerId = response?.customer?.id ?: -1,
    firstName = response?.customer?.firstName,
    lastLame = response?.customer?.lastLame,
    email = response?.customer?.email,
    gender = response?.customer?.gender,
    bikeTypes = response?.bikeTypes,
    frameTypes = response?.frameTypes,
    duration = response?.duration,
    requiredAccessories = response?.accessories?.requiredAccessories?.map { it.toDM() },
    isLeasingAccessories = response?.accessories?.isLeasingAccessories,
    mdrAccessories = response?.accessories?.mdrAccessories?.filterNotNull(),
    notAllowedAccessories = response?.accessories?.notAllowedAccessories,
    employeeBudget = response?.employeeBudget,
    servicePackages = response?.servicePackages?.map { it.toDM() },
    creditors = response?.creditors?.map { it.toDM() }
)

fun RequireAccessory.toDM(): RequireAccessoryDM = RequireAccessoryDM(price, requiredAccessoryCategory)
fun ServicePackage.toDM(): ServicePackageDM = ServicePackageDM(id, title, price)
fun Creditor.toDM(): CreditorDM = CreditorDM(id, name)