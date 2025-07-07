package com.mdrapp.de.ui.home.map.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class StoreClusterItem(
    private val lat: Double,
    private val lng: Double,
    val titleStore: String,
    val snippetStore: String,
) : ClusterItem {
    override fun getPosition(): LatLng = LatLng(lat, lng)
    override fun getTitle(): String = titleStore
    override fun getSnippet(): String = snippetStore
    override fun getZIndex(): Float? = null
}