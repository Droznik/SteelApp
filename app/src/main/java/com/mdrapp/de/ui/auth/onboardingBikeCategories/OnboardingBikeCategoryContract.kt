package com.mdrapp.de.ui.auth.onboardingBikeCategories

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class OnboardingBikeState(
    val categories: ImmutableList<OnboardingBikeCategoryItemVM> = persistentListOf()
)

sealed interface OnboardingBikeEvent {
    data object OnContinueBtnClick: OnboardingBikeEvent
}