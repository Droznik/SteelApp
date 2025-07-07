package com.mdrapp.de.navigation


sealed class RootNavHost(val route: String) {
    data object Splash : RootNavHost("splash")
    data object HowTo : RootNavHost("howto_graph")
    data object Auth : RootNavHost("auth_graph")
    data object Home : RootNavHost("home")
    data object DealerLocation : RootNavHost("dealer_location_graph")
}

sealed class HowToGraph(val route: String) {
    data object HowTo1 : HowToGraph("howto_1")
    data object HowTo2 : HowToGraph("howto_2")
}

sealed class AuthGraph(val route: String) {
    data object Login : AuthGraph("login")
    data object PartnerCode : AuthGraph("partnerCode")
    data object ForgotPassword : AuthGraph("forgot_password")
    data object SuccessResetPassword : AuthGraph("success_reset_password")
    data object Register : AuthGraph("register")
    data object EmailConfirm : AuthGraph("email_confirmation")
    data object EmailConfirmSuccess : AuthGraph("email_confirmation_success")
    data object LastStep : AuthGraph("last_step")
    data object MyCategories : AuthGraph("auth_my_categories")
}

sealed class DealerLocationGraph(val route: String) {
    data object DealerLocation : DealerLocationGraph("dealer_location")
    data object DealerLocationDetails : DealerLocationGraph("dealer_location_details")
}