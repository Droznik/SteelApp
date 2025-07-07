package com.mdrapp.de.data.model.remote.product

import com.google.gson.annotations.SerializedName
import com.mdrapp.de.domain.model.BikeColorDM
import com.mdrapp.de.domain.model.BikeSizeDM
import com.mdrapp.de.domain.model.CatalogProductDM
import com.mdrapp.de.domain.model.ProductDM
import com.mdrapp.de.domain.model.ProductVariantDM
import com.mdrapp.de.domain.model.PropertyValueDM
import com.mdrapp.de.domain.model.SliderProductDM

data class Product(
    @SerializedName("id") val id: Long,
    @SerializedName("display_name") val displayName: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("brand") val brand: String? = null,
    @SerializedName("manufacturer") val manufacturer: String? = null,
    @SerializedName("colors") val colors: List<BikeColor?>? = null,
    @SerializedName("variants") val variants: List<ProductVariant?>? = null,
)

data class BikeColor(
    @SerializedName("name") val name: String? = null,
    @SerializedName("sizes") val sizes: List<BikeSize?>? = null,
)

data class BikeSize(
    @SerializedName("bike_variant_id") val bikeVariantId: Long,
    @SerializedName("name") val name: String? = null,
    @SerializedName("size_recommend_from") val sizeRecommendFrom: String? = null,
    @SerializedName("size_recommend_to") val sizeRecommendTo: String? = null,
    @SerializedName("availability_description") val availabilityDescription: String? = null,
)

data class ProductVariant(
    @SerializedName("id") val id: Long,
    @SerializedName("bike_main_product_id") val productId: Long,
    @SerializedName("uvp") val uvp: Double? = null,
    @SerializedName("purchase_price") val purchasePrice: Double? = null,
    @SerializedName("leasing_rate") val leasingRate: Double? = null,
    @SerializedName("special_leasing_rate") val specialLeasingRate: Double? = null,
    @SerializedName("computed") val computed: Computed? = null,
    @SerializedName("on_wishlist") val isFavourite: Boolean? = null,
    @SerializedName("size") val size: String? = null,
    @SerializedName("state") val state: String? = null,
    @SerializedName("short_description") val shortDescription: String? = null,
    @SerializedName("long_description") val longDescription: String? = null,
    @SerializedName("availability_description") val availabilityDescription: String? = null,
    @SerializedName("stock") val stock: Int? = null,
    @SerializedName("colors") val colors: List<String?>? = null,
    @SerializedName("display_name") val displayName: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("category") val category: String? = null,
    @SerializedName("brand") val brand: String? = null,
    @SerializedName("manufacturer") val manufacturer: String? = null,
    @SerializedName("variants") val variants: List<BikeColor?>? = null,
    @SerializedName("bidex_image_url") val imageUrl: String? = null,
    @SerializedName("property_values") val propertyValues: List<PropertyValue?>? = null,
    @SerializedName("brand_image_path") val brandImagePath: String? = null,
    @SerializedName("product_images") val productImages: List<String?>? = null
)

data class Computed(
    @SerializedName("leasing_rate") val leasingRate: Double? = null,
    @SerializedName("insurance_rate") val insuranceRate: Double? = null,
)

data class PropertyValue(
    @SerializedName("name") val name: String? = null,
    @SerializedName("value") val value: String? = null,
)

fun ProductVariant.toSliderProductDM(): SliderProductDM = SliderProductDM(
    id = productId,
    displayName = displayName,
    category = category,
    imageUrl = imageUrl ?: productImages?.firstOrNull(),
)

fun ProductVariant.toCatalogProductDM(): CatalogProductDM {
    val props = propertyValues?.associateBy({ it?.name }, {it?.value}).orEmpty()

    return CatalogProductDM(
        id = productId,
        variantId = id,
        displayName = displayName,
        type = type,
        category = category,
        label = brandImagePath,
        imgUrl = imageUrl ?: productImages?.firstOrNull(),
        size = size,
        sizeVariants = variants?.firstOrNull()?.sizes?.mapNotNull { it?.name }?.joinToString(),
        color = colors?.takeIf { it.isNotEmpty() }?.get(0),
        price = computed?.leasingRate,
        specialPrice = specialLeasingRate,
        isFavourite = isFavourite ?: false,
        props = props
    )
}

fun Product.toDomain(): ProductDM = ProductDM(
    id = id,
    displayName = displayName,
    type = type,
    category = category,
    brand = brand,
    label = variants?.firstOrNull()?.brandImagePath,
    manufacturer = manufacturer,
    colors = colors?.mapNotNull { it?.toDomain() },
    variants = variants?.mapNotNull { it?.toDomain() },
)

fun ProductVariant.toDomain(): ProductVariantDM = ProductVariantDM(
    id = id,
    productId = productId,
    uvp = uvp,
    purchasePrice = purchasePrice,
    leasingRate = leasingRate,
    specialLeasingRate = specialLeasingRate,
    computedLeasingRate = computed?.leasingRate,
    computedInsuranceRate = computed?.insuranceRate,
    isFavourite = isFavourite ?: false,
    size = size,
    state = state,
    shortDescription = shortDescription,
    longDescription = longDescription,
    availabilityDescription = availabilityDescription,
    stock = stock,
    colors = colors?.filterNotNull(),
    displayName = displayName,
    type = type,
    category = category,
    brand = brand,
    manufacturer = manufacturer,
    imageUrl = imageUrl,
    propertyValues = propertyValues?.mapNotNull { it?.toDomain() },
    productImages = productImages
)

fun PropertyValue.toDomain(): PropertyValueDM = PropertyValueDM(name, value)

fun BikeColor.toDomain(): BikeColorDM = BikeColorDM(
    name = name,
    sizes = sizes?.mapNotNull { it?.toDomain() }
)

fun BikeSize.toDomain(): BikeSizeDM = BikeSizeDM(bikeVariantId, name, availabilityDescription, sizeRecommendFrom, sizeRecommendTo)
