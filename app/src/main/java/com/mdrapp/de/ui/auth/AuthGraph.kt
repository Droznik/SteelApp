package com.mdrapp.de.ui.auth

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mdrapp.de.ext.sharedViewModel
import com.mdrapp.de.navigation.AuthGraph
import com.mdrapp.de.navigation.RootNavHost
import com.mdrapp.de.ui.auth.login.forgotPassword.ForgotPasswordFragment
import com.mdrapp.de.ui.auth.login.forgotPassword.ForgotPasswordViewModel
import com.mdrapp.de.ui.auth.login.LoginFragment
import com.mdrapp.de.ui.auth.login.LoginViewModel
import com.mdrapp.de.ui.auth.login.successResetPassword.SuccessResetPasswordFragment
import com.mdrapp.de.ui.auth.onboardingBikeCategories.OnboardingBikeCategoryFragment
import com.mdrapp.de.ui.auth.onboardingBikeCategories.OnboardingBikeCategoryViewModel
import com.mdrapp.de.ui.auth.registration.RegistrationViewModel
import com.mdrapp.de.ui.auth.registration.fragment.EmailConfirmationFragment
import com.mdrapp.de.ui.auth.registration.fragment.EmailConfirmedFragment
import com.mdrapp.de.ui.auth.registration.fragment.LastStepFragment
import com.mdrapp.de.ui.auth.registration.fragment.PartnerCodeFragment
import com.mdrapp.de.ui.auth.registration.fragment.RegistrationFragment

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = AuthGraph.Login.route, route = RootNavHost.Auth.route) {
        composable(AuthGraph.Login.route) {
            val vm = hiltViewModel<LoginViewModel>()
            LoginFragment(navController, vm)
        }
        composable(AuthGraph.Register.route) {
            val vm = it.sharedViewModel<RegistrationViewModel>(navController = navController)
            RegistrationFragment(navController = navController, vm = vm)
        }
        composable(AuthGraph.ForgotPassword.route) {
            val vm = hiltViewModel<ForgotPasswordViewModel>()
            ForgotPasswordFragment(navController = navController, vm = vm)
        }
        composable(AuthGraph.SuccessResetPassword.route) {
            SuccessResetPasswordFragment(navController = navController)
        }
        composable(AuthGraph.MyCategories.route) {
            val vm = hiltViewModel<OnboardingBikeCategoryViewModel>()
            OnboardingBikeCategoryFragment(navController = navController, vm = vm)
        }
        composable(AuthGraph.PartnerCode.route) {
            val vm = it.sharedViewModel<RegistrationViewModel>(navController = navController)
            PartnerCodeFragment(navController = navController, vm = vm)
        }
        composable(AuthGraph.EmailConfirm.route) {
            val vm = it.sharedViewModel<RegistrationViewModel>(navController = navController)
            EmailConfirmationFragment(navController = navController, vm = vm)
        }
        composable(AuthGraph.EmailConfirmSuccess.route) {
            val vm = it.sharedViewModel<RegistrationViewModel>(navController = navController)
            EmailConfirmedFragment(navController = navController, vm = vm)
        }
        composable(AuthGraph.LastStep.route) {
            val vm = it.sharedViewModel<RegistrationViewModel>(navController = navController)
            LastStepFragment(navController = navController, vm = vm)
        }
    }
}