package com.mdrapp.de.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mdrapp.de.domain.util.BikeType
import com.mdrapp.de.ui.home.myBikes.MyMdrBikesType


const val PRODUCT_ID_ARG = "product_id"
const val OFFER_ID_ARG = "offer_id"
const val MY_MDR_BIKE_ID_ARG = "my_mdr_bike_id"
const val MY_MDR_BIKES_TYPE_ARG = "my_mdr_bikes_type"
const val MY_MDR_BIKES_NUMBER_ARG = "my_mdr_bikes_number"


sealed class HomeNavHost(val route: String) {
    data object Home : HomeNavHost("shop")

    data object Product : HomeNavHost("product/{$PRODUCT_ID_ARG}") {
        val arguments = listOf(navArgument(PRODUCT_ID_ARG) { type = NavType.LongType })
        fun createRoute(id: Long) = "product/$id"
    }

    data object Purchase : HomeNavHost("purchase")
    data object MyOffers : HomeNavHost("my_offers")
    data object MyServiceCases : HomeNavHost("service_cases")
    data object Order : HomeNavHost("order_graph")
    data object Profile : HomeNavHost("profile")
    data object Favourites : HomeNavHost("favourites")
    data object Faq : HomeNavHost("faq")
    data object Support : HomeNavHost("support")

    data object LeasingCalculator : HomeNavHost("leasing_calculator")
    data object BikePass : HomeNavHost("bike_pass")
    data object MyMdrBikes : HomeNavHost("my_mdr_bikes/{$MY_MDR_BIKES_TYPE_ARG}") {
        fun createRoute(type: MyMdrBikesType) = "my_mdr_bikes/${type.name}"
    }

    data object Category: HomeNavHost("category_bikes") {
        fun createRoute(
            type: BikeType,
            title: String,
            categories: String
        ) = route.also {
//        CatalogViewModel.catalogArg = CatalogARG(
//            catalogType = CatalogType.Category,
//            bikeType = type,
//            title = title,
//            params = when {
//                categories.isNotBlank() -> mapOf(
//                    "category" to categories,
//                    "type" to type.name
//                )
//                else -> mapOf(
//                    "type" to type.name
//                )
//            }
//        )
        }
    }

    data object TopBikes : HomeNavHost("top_bikes") {
        fun createRoute(type: BikeType) = route.also {
//        CatalogViewModel.catalogArg = CatalogARG(
//            catalogType = CatalogType.Top,
//            bikeType = type,
//            params = mapOf("type" to type.name)
//        )
        }
    }
    data object SpecialOffer : HomeNavHost("special_offer") {
        fun createRoute(type: BikeType) = route.also {
//        val params = when (type) {
//            BikeType.None -> mapOf()
//            else -> mapOf("type" to type.name)
//        }
//        CatalogViewModel.catalogArg = CatalogARG(
//            catalogType = CatalogType.Offers,
//            bikeType = type,
//            params = params
//        )
        }
    }

    data object ActionCarousel : HomeNavHost("action_carousel") {
        fun createRoute(type: BikeType) = route.also {
//        CatalogViewModel.catalogArg = CatalogARG(
//            catalogType = CatalogType.Actions,
//            bikeType = type,
//            params = mapOf("type" to type.name)
//        )
        }
    }
}

sealed class PurchaseGraph(val route: String) {
    data object Variant : PurchaseGraph("purchase_variant")
    data object Order : PurchaseGraph("purchase_order?$OFFER_ID_ARG={$OFFER_ID_ARG}") {
        val arguments = listOf(navArgument(OFFER_ID_ARG) {
            type = NavType.LongType
            defaultValue = -1L
        })
        fun createRoute(offerId: Long) = "purchase_order?$OFFER_ID_ARG=$offerId"
    }
    data object Result : PurchaseGraph("purchase_result")
}

sealed class MyOffersGraph(val route: String) {
    data object OfferList : MyOffersGraph("my_offers_list")
    data object OfferDetail : MyOffersGraph("offer/{$OFFER_ID_ARG}") {
        val arguments = listOf(navArgument(OFFER_ID_ARG) { type = NavType.LongType })
        fun createRoute(id: Long) = "offer/$id"
    }
}

sealed class MyServiceCasesGraph(val route: String) {
    data object ServiceCases : MyServiceCasesGraph("service_cases_list")
    data object ServiceCaseDetail : MyServiceCasesGraph("service_case_detail")
}

sealed class MyMdrBikesGraph(val route: String) {
    data object List : MyMdrBikesGraph("my_mdr_bikes_list")
    data object Detail : MyMdrBikesGraph("my_mdr_bike_detail/{$MY_MDR_BIKE_ID_ARG}/{$MY_MDR_BIKES_NUMBER_ARG}/{$MY_MDR_BIKES_TYPE_ARG}") {
        val arguments = listOf(
            navArgument(MY_MDR_BIKE_ID_ARG) { type = NavType.LongType },
            navArgument(MY_MDR_BIKES_NUMBER_ARG) { type = NavType.StringType },
            navArgument(MY_MDR_BIKES_TYPE_ARG) { type = NavType.StringType }
        )
        fun createRoute(id: Long, number: String, type: MyMdrBikesType) = "my_mdr_bike_detail/$id/$number/${type.name}"
    }
}

sealed class OrderGraph(val route: String) {
    data object Home : OrderGraph("order_home")
    data object Extras : OrderGraph("order_extras")
    data object Customer : OrderGraph("order_customer")
    data object Delivery : OrderGraph("order_delivery")
    data object Confirm : OrderGraph("order_confirm")
    data object Checkout : OrderGraph("order_checkout")
    data object Result : OrderGraph("order_result")
    data object FinalStep1 : OrderGraph("order_final_step_1")
    data object FinalStep2 : OrderGraph("order_final_step_2")
    data object FinalStep3 : OrderGraph("order_final_step_3")
}