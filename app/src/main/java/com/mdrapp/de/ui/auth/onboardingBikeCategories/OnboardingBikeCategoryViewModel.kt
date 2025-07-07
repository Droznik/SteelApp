package com.mdrapp.de.ui.auth.onboardingBikeCategories

import com.mdrapp.de.common.viewmodel.ContractViewModel
import com.mdrapp.de.domain.storage.SessionStorage
import com.mdrapp.de.navigation.AuthGraph
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.navigation.NavEvent
import com.mdrapp.de.navigation.RootNavHost
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class OnboardingBikeCategoryViewModel @Inject constructor(
    private val sessionStorage: SessionStorage
) : ContractViewModel<OnboardingBikeState, OnboardingBikeEvent>() {

    override val initialState = OnboardingBikeState()
    override fun onEvent(event: OnboardingBikeEvent) {
        when (event) {
            OnboardingBikeEvent.OnContinueBtnClick -> goNext()
        }
    }

    fun handleCategories(categories: ImmutableList<OnboardingBikeCategoryItemVM>) = simpleLaunch {
        val savedCategories: List<String>? = withContext(Dispatchers.IO) { sessionStorage.getCategories() }

        if (!savedCategories.isNullOrEmpty()) {
            categories.forEach { it.isSelected = it.id in savedCategories }
        }

        _state.update { it.copy(categories = categories) }
    }

    private fun goNext() = simpleLaunch {
        val selectedCategories = state.value.categories.filter { it.isSelected }.map { it.id }
        withContext(Dispatchers.IO) { sessionStorage.setCategories(selectedCategories) }
        navigate(NavEvent.To(RootNavHost.Home.route))
    }

}