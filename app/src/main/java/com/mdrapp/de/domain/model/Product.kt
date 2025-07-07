package com.mdrapp.de.domain.model


data class SliderProductDM(
    val id: Long,
    val displayName: String?,
    val category: String?,
    val imageUrl: String?,
)

data class CatalogProductDM(
    val id: Long,
    val variantId: Long,
    val displayName: String?,
    val type: String?,
    val category: String?,
    val label: String?,
    val imgUrl: String?,
    val size: String?,
    val sizeVariants: String?,
    val color: String?,
    val price: Double?,
    val specialPrice: Double?,
    val isFavourite: Boolean,
    val props: Map<String?, String?>
)

data class ProductDM(
    val id: Long,
    val displayName: String?,
    val type: String?,
    val category: String?,
    val brand: String?,
    val label: String?,
    val manufacturer: String?,
    val colors: List<BikeColorDM>?,
    val variants: List<ProductVariantDM>?
)

data class BikeColorDM(
    val name: String?,
    val sizes: List<BikeSizeDM>?
)

data class BikeSizeDM(
    val bikeVariantId: Long,
    val name: String?,
    val availabilityDescription: String?,
    val sizeRecommendedFrom: String?,
    val sizeRecommendedTo: String?
)

data class ProductVariantDM(
    val id: Long,
    val productId: Long,
    val uvp: Double?,
    val purchasePrice: Double?,
    val leasingRate: Double?,
    val specialLeasingRate: Double?,
    val computedLeasingRate: Double?,
    val computedInsuranceRate: Double?,
    var isFavourite: Boolean,
    val size: String?,
    val state: String?,
    val shortDescription: String?,
    val longDescription: String?,
    val availabilityDescription: String?,
    val stock: Int?,
    val colors: List<String>?,
    val displayName: String?,
    val type: String?,
    val category: String?,
    val brand: String?,
    val manufacturer: String?,
    val imageUrl: String?,
    val propertyValues: List<PropertyValueDM>?,
    val productImages: List<String?>?
)

data class PropertyValueDM(
    val name: String?,
    val value: String?
)

data class ProductFilterDM(
    val id: Long?,
    val internalName: String?,
    val deName: String?,
    val enName: String?,
    val filterType: String?,
    val type: String?,
    val values: List<String>?,
)