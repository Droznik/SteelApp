package com.mdrapp.de.data

import com.mdrapp.de.data.interceptors.mdr.HEADER_AUTH_REGISTER
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.data.model.remote.BikePassResponse
import com.mdrapp.de.data.model.remote.IdRequest
import com.mdrapp.de.data.model.remote.login.LoginRequest
import com.mdrapp.de.data.model.remote.login.LoginResponse
import com.mdrapp.de.data.model.remote.map.MapLocationResponse
import com.mdrapp.de.data.model.remote.map.MapRadarRequest
import com.mdrapp.de.data.model.remote.map.MapRadarResponse
import com.mdrapp.de.data.model.remote.offers.GetOfferDetailResponse
import com.mdrapp.de.data.model.remote.offers.GetOfferListResponse
import com.mdrapp.de.data.model.remote.offers.GetOfferOrderInfoResponse
import com.mdrapp.de.data.model.remote.profile.ProfileInfoResponse
import com.mdrapp.de.data.model.remote.purchase.CreateOrderRequest
import com.mdrapp.de.data.model.remote.purchase.GetOrderResponse
import com.mdrapp.de.data.model.remote.purchase.SetCalculationsRequest
import com.mdrapp.de.data.model.remote.purchase.SetCalculationsResponse
import com.mdrapp.de.data.model.remote.registration.EmailConfirmationResponse
import com.mdrapp.de.data.model.remote.registration.FinishRegistrationRequest
import com.mdrapp.de.data.model.remote.registration.FinishRegistrationResponse
import com.mdrapp.de.data.model.remote.registration.LastStepResponse
import com.mdrapp.de.data.model.remote.registration.RegistrationRequest
import com.mdrapp.de.data.model.remote.registration.RegistrationResponse
import com.mdrapp.de.data.model.remote.registration.ShopCodeRequest
import com.mdrapp.de.data.model.remote.registration.ShopCodeResponse
import com.mdrapp.de.data.model.remote.resendMail.ResendMailRequest
import com.mdrapp.de.data.model.remote.resendMail.ResendMailResponse
import com.mdrapp.de.data.model.remote.resetPassword.ResetPasswordRequest
import com.mdrapp.de.data.model.remote.resetPassword.ResetPasswordResponse
import com.mdrapp.de.data.model.remote.mdrOrders.GetMdrOrderDetailResponse
import com.mdrapp.de.data.model.remote.mdrOrders.GetMdrOrderFileResponse
import com.mdrapp.de.data.model.remote.mdrOrders.GetMdrOrdersResponse
import com.mdrapp.de.data.model.remote.mdrOrders.UploadOrderFileRequest
import com.mdrapp.de.data.model.remote.serviceCase.ServiceCaseDetailResponse
import com.mdrapp.de.data.model.remote.serviceCase.ServiceCaseListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Path


interface MdrApiService {
    @POST("register/validate/shopcode")
    suspend fun getShopCode(@Body body: ShopCodeRequest): ShopCodeResponse

    @POST("register")
    suspend fun register(@Body body: RegistrationRequest): RegistrationResponse

    @POST("check/verified/email")
    @Headers(HEADER_AUTH_REGISTER)
    suspend fun validateEmail(): EmailConfirmationResponse

    @POST("register/last-step")
    @Headers(HEADER_AUTH_REGISTER)
    suspend fun registerLastStep(): LastStepResponse

    @POST("register/finished")
    @Headers(HEADER_AUTH_REGISTER)
    suspend fun finishRegistration(@Body body: FinishRegistrationRequest): FinishRegistrationResponse

    @POST("login")
    suspend fun login(@Body body: LoginRequest) : LoginResponse

    @POST("ResendMailVerified")
    suspend fun resendMail(@Body body: ResendMailRequest): ResendMailResponse

    @POST("forgot-password")
    suspend fun resetPassword(@Body body: ResetPasswordRequest): ResetPasswordResponse

    @POST("user/get/profile")
    suspend fun getUserProfile(): ProfileInfoResponse

    /* purchase */

    @GET("order/check")
    suspend fun getOrderCheck(): BaseResponse

    @GET("order")
    suspend fun getOrder(): GetOrderResponse

    @POST("dienstrader/order/getFieldList")
    suspend fun getOfferOrder(@Body body: IdRequest): GetOfferOrderInfoResponse

    @POST("order")
    suspend fun createOrder(@Body body: CreateOrderRequest): BaseResponse

    @POST("order/calculations")
    suspend fun calculate(@Body body: SetCalculationsRequest): SetCalculationsResponse

    /* store location */
    @GET("map/get/address")
    suspend fun getAddresses(@Query(value = "zip") value: String): MapLocationResponse

    @POST("map/get/radarAddresses")
    suspend fun radarAddress(@Query(value = "radius") value: String, @Body body: MapRadarRequest): MapRadarResponse

    /* my orders, my bikes */

    @GET("orders")
    suspend fun getMdrOrders(@Query("type") type: String): GetMdrOrdersResponse

    @GET("orders/{id}")
    suspend fun getMdrOrderDetail(@Path("id") id: Long): GetMdrOrderDetailResponse

    @GET("orders/doc/{id}")
    suspend fun getMdrOrderFile(@Path("id") id: Long): GetMdrOrderFileResponse

    @POST("orders/doc")
    suspend fun uploadDocument(@Body body: UploadOrderFileRequest): BaseResponse

    /* my offers */

    @POST("dienstrader/get/list")
    suspend fun getOfferList(): GetOfferListResponse

    @POST("dienstrader/order/delite")
    suspend fun deleteOffer(@Body body: IdRequest): BaseResponse

    @POST("dienstrader/get/detail")
    suspend fun getOfferDetail(@Body body: IdRequest): GetOfferDetailResponse

    @GET("bikepass")
    suspend fun getBikePass(): BikePassResponse

    /* service cases */
    @POST("Serviceubersicht/get/detail")
    suspend fun getServiceCaseDetail(@Body body: IdRequest): ServiceCaseDetailResponse

    @POST("Serviceubersicht/get/list")
    suspend fun getServiceCaseList(): ServiceCaseListResponse

}