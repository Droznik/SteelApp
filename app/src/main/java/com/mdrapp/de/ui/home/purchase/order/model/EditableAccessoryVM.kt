package com.mdrapp.de.ui.home.purchase.order.model

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mdrapp.de.domain.model.OfferAccessoryDM
import com.mdrapp.de.domain.model.RequireAccessoryDM
import com.mdrapp.de.ext.formattedAsDouble


@Stable
data class EditableAccessoryVM(
    val id: Int,
    val displayCategory: String = "",
    val isRequiredAccessory: Boolean = false,
    val initialUvp: Double? = null,
    val readOnly: Boolean = false
) {
    var title by mutableStateOf("")
    var uvp by mutableStateOf(initialUvp?.toString() ?: "")
    var isUvpError by mutableStateOf(false)
    var price by mutableStateOf("")
    var quantity by mutableIntStateOf(1)
    val category = when {
        isRequiredAccessory -> "Pflichtzubehör: $displayCategory"
        else -> "Leasingfähiges Zubehör: ja"
    }

    fun validated() = title.isNotBlank() || (price.isNotBlank()) || getUvpDoubleOrNull() != null
    fun getUvpDoubleOrNull(): Double? {
        val uvpDouble: Double? = uvp.toDoubleOrNull()

        return when(isRequiredAccessory) {
            true -> if (isUvpError) null else uvpDouble
            else -> uvpDouble
        }
    }

    fun onUvpChange(value: String) {
        uvp = value.formattedAsDouble(uvp)
        if (isRequiredAccessory) {
            isUvpError = (initialUvp ?: 0.0) > (uvp.toDoubleOrNull() ?: 0.0)
        }
    }

    fun onPriceChange(value: String) {
        price = value.formattedAsDouble(price)
    }

    fun plus() {
        quantity += 1
    }

    fun minus(): Boolean = when {
        quantity > 1 -> {
            quantity -= 1
            true
        }
        else -> false
    }
}

fun RequireAccessoryDM.toVM(id: Int): EditableAccessoryVM = EditableAccessoryVM(
    id = id,
    displayCategory = category.orEmpty(),
    isRequiredAccessory = true,
    initialUvp = price
)

fun OfferAccessoryDM.toVM(
    id: Int,
    isRequiredAccessory: Boolean = false
) : EditableAccessoryVM = EditableAccessoryVM(
    id = id,
    displayCategory = category.orEmpty(),
    isRequiredAccessory = isRequiredAccessory,
    initialUvp = uvp,
    readOnly = true
).apply {
    title = this@toVM.title.orEmpty()
    price = this@toVM.price?.toString().orEmpty()
    quantity = this@toVM.quantity ?: 1
}