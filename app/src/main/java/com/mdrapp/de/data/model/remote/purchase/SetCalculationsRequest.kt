package com.mdrapp.de.data.model.remote.purchase

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.domain.model.CalculationAccessoryDM
import com.mdrapp.de.domain.model.SetCalculationsDM

data class SetCalculationsRequest(
    @SerializedName("from_customer") val customerId: Long,
    @SerializedName("from_creditor") val creditorId: Long,
    @SerializedName("purchase_price_brutto") val price: Double,
    @SerializedName("bike_type") val bikeType: String,
    @SerializedName("leasing_duration") val duration: Int,
    @SerializedName("uvp_brutto") val uvp: Double,
    @SerializedName("employee_budget_brutto") val employeeBudget: Double?,
    @SerializedName("accessories") val accessories: List<CalculationAccessory>?,
)

data class CalculationAccessory(
    @SerializedName("price_uvp") val uvp: Double,
    @SerializedName("price") val price: Double,
    @SerializedName("quantity") val quantity: Int,
)

fun SetCalculationsDM.toData(): SetCalculationsRequest = SetCalculationsRequest(
    customerId = customerId,
    creditorId = creditorId,
    price = price,
    bikeType = bikeType,
    duration = duration,
    uvp = uvp,
    employeeBudget = employeeBudget,
    accessories = accessories?.map { it.toData() },
)

fun CalculationAccessoryDM.toData(): CalculationAccessory = CalculationAccessory(uvp, price, quantity)
