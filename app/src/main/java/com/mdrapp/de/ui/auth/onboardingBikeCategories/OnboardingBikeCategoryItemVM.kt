package com.mdrapp.de.ui.auth.onboardingBikeCategories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.Serializable

data class OnboardingBikeCategoryItemVM(
    val id: String,
    val title: String,
    val initialIsSelected: Boolean = false
): Serializable {
    var isSelected by mutableStateOf(initialIsSelected)
}