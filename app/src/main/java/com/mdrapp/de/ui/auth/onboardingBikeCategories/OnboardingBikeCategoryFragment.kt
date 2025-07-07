package com.mdrapp.de.ui.auth.onboardingBikeCategories

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.util.rememberSaveableImmutableListOf
import com.mdrapp.de.ui.views.LogoTopBar
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.TextLineHorizontalDivider
import kotlinx.collections.immutable.ImmutableList

@Composable
fun OnboardingBikeCategoryFragment(
    navController: NavController,
    vm: OnboardingBikeCategoryViewModel
) {
    BaseFragment(baseViewModel = vm, navController = navController) {
        val state by vm.state.collectAsState()
        val onEvent = remember { vm::onEvent }
        val categories = rememberCategories(stringArrayResource(R.array.bike_categories))


        LaunchedEffect(Unit) { vm.handleCategories(categories) }
        OnboardingBikeCategoryScreen(state = state, onEvent = onEvent)
    }
}

@Composable
fun OnboardingBikeCategoryScreen(
    state: OnboardingBikeState,
    onEvent: (OnboardingBikeEvent) -> Unit
) {
    Column {
        LogoTopBar()
        Spacer(Modifier.height(27.dp))
        Title(title = stringResource(id = R.string.personal_recommendations_title))
        Spacer(Modifier.height(12.dp))
        Description(description = stringResource(id = R.string.personal_recommendations_description))
        Spacer(Modifier.height(27.dp))
        RecommendationCategories( modifier = Modifier.padding(horizontal = 15.dp).fillMaxWidth(), categories = state.categories)
        Spacer(Modifier.weight(1f))
        TextLineHorizontalDivider(text = stringResource(id = R.string.and_now), modifier = Modifier.padding(horizontal = 20.dp))
        Spacer(Modifier.height(22.dp))
        PrimaryButton(modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(), text = stringResource(id = R.string.personal_recommendations_remember_selection))
        { onEvent(OnboardingBikeEvent.OnContinueBtnClick) }
        Spacer(Modifier.height(42.dp))
    }
}

@Composable
private fun Title(title: String) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = title,
        color = MDRTheme.colors.titleText,
        style = MDRTheme.typography.title
    )
}

@Composable
private fun Description(description: String) {
    Text(
        modifier = Modifier.padding(horizontal = 20.dp),
        text = description,
        color = MDRTheme.colors.primaryText,
        style = MDRTheme.typography.description
    )
}

@Composable
fun RecommendationCategories(
    modifier: Modifier,
    categories: ImmutableList<OnboardingBikeCategoryItemVM>,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        items(categories) {
            OnboardingBikeItem(it)
        }
    }
}

@Composable
fun rememberCategories(categories: Array<String>): ImmutableList<OnboardingBikeCategoryItemVM> {
    val list = categories.map {
        val splitList = it.split('|')
        OnboardingBikeCategoryItemVM(splitList[0], splitList[1])
    }

    return rememberSaveableImmutableListOf(list)
}