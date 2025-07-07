package com.mdrapp.de.data.model.remote.offers

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.data.model.remote.baseResponse.mdr.BaseResponse
import com.mdrapp.de.domain.model.OfferDetailAccessoryDM
import com.mdrapp.de.domain.model.OfferDetailAccessoryInfoDM
import com.mdrapp.de.domain.model.OfferDetailBikeDM
import com.mdrapp.de.domain.model.OfferDetailCalculationDM
import com.mdrapp.de.domain.model.OfferDetailDM
import com.mdrapp.de.domain.model.OfferDetailDealerDM


data class GetOfferDetailResponse(
    @SerializedName("response") val response: OfferDetailResponse? = null
) : BaseResponse()

data class OfferDetailResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("AngebotGultigBis") val offerValidUntil: String? = null,
    @SerializedName("KaufpreisGesamt") val purchaseTotalPrice: Double? = null,
    @SerializedName("Leasingrate") val leasingRate: Double? = null,
    @SerializedName("Fahrrad") val bike: OfferDetailBike? = null,
    @SerializedName("Zubehör") val accessoryInfo: OfferDetailAccessoryInfo? = null,
    @SerializedName("Fachhändler") val dealer: OfferDetailDealer? = null,
    @SerializedName("Kalkulation") val calculation: OfferDetailCalculation? = null,
)

data class OfferDetailBike(
    @SerializedName("Model") val model: String? = null,
    @SerializedName("Typ") val type: String? = null,
    @SerializedName("Marke") val brand: String? = null,
    @SerializedName("Rahmenart") val frameType: String? = null,
    @SerializedName("Kategorie") val category: String? = null,
    @SerializedName("Rahmengrobe") val frameSize: String? = null,
    @SerializedName("Farbe") val color: String? = null,
)

data class OfferDetailAccessoryInfo(
    @SerializedName("Accessories") val accessories: List<OfferDetailAccessory>? = null,
    @SerializedName("GesamtpreisNetto") val totalPriceNet: Double? = null,
    @SerializedName("GesamtpreisBrutto") val totalPriceGross: Double? = null,
    @SerializedName("UVP") val uvp: Double? = null,
)

data class OfferDetailAccessory(
    @SerializedName("Bezeichnung") val title: String? = null,
    @SerializedName("Menge") val quantity: Int? = null,
    @SerializedName("Verkaufspreis Netto") val netSellingPrice: Double? = null,
    @SerializedName("Verkaufspreis Brutto") val grossSalesPrice: Double? = null,
    @SerializedName("Verkaufspreis Brutto inkl") val salesPriceGrossIncl: String? = null,
    @SerializedName("UVP") val uvp: Double? = null,
)

data class OfferDetailDealer(
    @SerializedName("name") val name: String? = null,
    @SerializedName("shipping_address") val shippingAddress: String? = null,
    @SerializedName("shipping_postcode") val shippingPostcode: String? = null,
    @SerializedName("shipping_city") val shippingCity: String? = null,
    @SerializedName("shipping_phone") val shippingPhone: String? = null,
    @SerializedName("website") val website: String? = null,
)

data class OfferDetailCalculation(
    @SerializedName("Leasingrate") val leasingRate: Double? = null,
    @SerializedName("Versicherungsrate") val insuranceRate: Double? = null,
    @SerializedName("Servicerate") val serviceRate: Double? = null,
    @SerializedName("AbzuglichArbeitgeberzuschuss") val lessEmployerContribution: Double? = null,
    @SerializedName("VomBruttoentgeltEinbehalten") val withheldFromGrossSalary: Double? = null,
    @SerializedName("GeldwerterVorteil") val pecuniaryAdvantage: Double? = null,
)

fun GetOfferDetailResponse.toDomain(): OfferDetailDM = OfferDetailDM(
    id = response?.id!!,
    offerValidUntil = response.offerValidUntil,
    purchaseTotalPrice = response.purchaseTotalPrice,
    leasingRate = response.leasingRate,
    bike = response.bike?.let {
        OfferDetailBikeDM(
            model = it.model,
            type = it.type,
            brand = it.brand,
            frameType = it.frameType,
            category = it.category,
            frameSize = it.frameSize,
            color = it.color,
        )
    },
    accessoryInfo = response.accessoryInfo?.let {
        OfferDetailAccessoryInfoDM(
            accessories = it.accessories?.map { accessory ->
                OfferDetailAccessoryDM(
                    title = accessory.title,
                    quantity = accessory.quantity,
                    netSellingPrice = accessory.netSellingPrice,
                    grossSalesPrice = accessory.grossSalesPrice,
                    salesPriceGrossIncl = accessory.salesPriceGrossIncl,
                    uvp = accessory.uvp,
                )
            },
            totalPriceNet = it.totalPriceNet,
            totalPriceGross = it.totalPriceGross,
            uvp = it.uvp,
        )
    },
    dealer = response.dealer?.let {
        OfferDetailDealerDM(
            name = it.name,
            shippingAddress = it.shippingAddress,
            shippingPostcode = it.shippingPostcode,
            shippingCity = it.shippingCity,
            shippingPhone = it.shippingPhone,
            website = it.website,
        )
    },
    calculation = response.calculation?.toDomain()
)

fun OfferDetailCalculation.toDomain(): OfferDetailCalculationDM = OfferDetailCalculationDM(
    leasingRate = leasingRate,
    insuranceRate = insuranceRate,
    serviceRate = serviceRate,
    lessEmployerContribution = lessEmployerContribution,
    withheldFromGrossSalary = withheldFromGrossSalary,
    pecuniaryAdvantage = pecuniaryAdvantage,
)