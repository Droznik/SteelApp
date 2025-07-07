package com.mdrapp.de.ui.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.HorizontalTextBlock
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.TextBlockDivider
import com.mdrapp.de.ui.views.TitleTopBar
import com.mdrapp.de.ui.views.VerticalTextBlock

@Composable
fun ProfileFragment(
    rootNavController: NavController,
    navController: NavController,
    vm: ProfileViewModel
) {
    BaseFragment(vm, navController) {
        val onEvent = remember { vm::onEvent }
        val state by vm.state.collectAsState()

        val logout = remember {
            {
                vm.onEvent(ProfileEvent.OnLogout(rootNavController))
            }
        }
        ProfileContent(logout, state, onEvent)
    }
}

@Composable
fun ProfileContent(
    logout: () -> Unit,
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    val tabs = listOf(
        stringResource(R.string.profile_data),
        stringResource(R.string.profile_your_company_bike)
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TitleTopBar(title = stringResource(R.string.profile_my_profile))
        UserImage(userName = state.name)
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        TabsRow(
            tabs = tabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { index -> selectedTabIndex = index },
            logout = logout,
            state = state,
            onEvent = onEvent
        )
    }
}


@Composable
private fun UserImage(imageUrl: String = "", userName: String) {
    val initials = userName.split(" ").joinToString("") { it.take(1).uppercase() }
    Box(
        modifier = Modifier
            .size(104.dp)
            .padding(20.dp)
            .clip(CircleShape)
            .background(MDRTheme.colors.appGreen, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = MDRTheme.colors.primaryBackground,
            fontSize = 27.sp,
            style = MDRTheme.typography.medium
        )
        AsyncImage(
            model = imageUrl,
            contentDescription = "User Profile Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun TabsRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    logout: () -> Unit,
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = selectedTabIndex == index
                val underlineColor = if (isSelected) MDRTheme.colors.primaryText else MDRTheme.colors.divider
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(MDRTheme.colors.primaryBackground)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onTabSelected(index) }
                        .padding(vertical = 16.dp)
                        .drawBehind {
                            val underlineOffset = 9.dp.toPx()
                            val underlineHeight = if (isSelected) 2.dp.toPx() else 1.dp.toPx()
                            val underlineY = size.height - underlineHeight + underlineOffset
                            drawLine(
                                color = underlineColor,
                                start = Offset(0f, underlineY),
                                end = Offset(size.width, underlineY),
                                strokeWidth = underlineHeight
                            )
                        }
                ) {
                    Text(
                        text = title,
                        style = if (isSelected) MDRTheme.typography.bold else MDRTheme.typography.regular,
                        fontSize = 18.sp,
                        color = MDRTheme.colors.primaryText,
                        textAlign = if (index == 0) TextAlign.Start else TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        when (selectedTabIndex) {
            0 -> UserDataTab(logout, state, onEvent)
            1 -> YourCompanyBikeTab(logout, state, onEvent)
        }
    }
}

@Composable
private fun UserDataTab(
    logout: () -> Unit,
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            HorizontalTextBlock(title = stringResource(R.string.profile_employer), value = state.employer)
            TextBlockDivider()
            HorizontalTextBlock(title = stringResource(R.string.profile_name), value = state.name)
            TextBlockDivider()
            HorizontalTextBlock(title = stringResource(R.string.profile_email), value = state.email)
            TextBlockDivider()
            HorizontalTextBlock(title = stringResource(R.string.profile_number), value = state.phone)
            TextBlockDivider()
            HorizontalTextBlock(title = stringResource(R.string.profile_address), value = state.address)
            TextBlockDivider()
            HorizontalTextBlock(title = stringResource(R.string.profile_personal_nr), value = state.personalNumber)
            TextBlockDivider()
            HorizontalTextBlock(title = stringResource(R.string.profile_land), value = state.country)
        }
        Spacer(modifier = Modifier.height(24.dp))
        PrimaryButton(
            text = stringResource(R.string.profile_request_changes),
            onClick = { onEvent(ProfileEvent.OnChangeRequested) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        PrimaryButton(
            text = stringResource(R.string.profile_two_factor),
            onClick = { onEvent(ProfileEvent.OnTwoFactorAuthClick) }
        )
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            modifier = Modifier
                .clickable { logout() }
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MDRTheme.typography.semiBold,
            fontSize = 18.sp,
            text = stringResource(R.string.logout)
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun YourCompanyBikeTab(
    logout: () -> Unit,
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            HorizontalTextBlock(title = stringResource(R.string.profile_allowed_types), value = state.allowed)
            TextBlockDivider()
            HorizontalTextBlock(title = stringResource(R.string.profile_min_max_price), value = state.minMaxPrice)
            TextBlockDivider()
            HorizontalTextBlock(title = stringResource(R.string.profile_basis), value = state.basis)
            TextBlockDivider()
            HorizontalTextBlock(title = stringResource(R.string.profile_price_limit), value = state.priceLimit)
            TextBlockDivider()
            HorizontalTextBlock(title = stringResource(R.string.profile_quantity), value = state.quantity)
            TextBlockDivider()
            VerticalTextBlock(title = stringResource(R.string.profile_employer_subsidies), value = state.employerSubsidies)
            TextBlockDivider()
            VerticalTextBlock(title = stringResource(R.string.profile_leasable), value = state.leasable)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier
                .clickable { logout() }
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MDRTheme.typography.semiBold,
            fontSize = 18.sp,
            text = stringResource(R.string.logout)
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview() {
    MDRTheme {
        ProfileContent(
            state = ProfileState(),
            onEvent = {},
            logout = {})
    }
}