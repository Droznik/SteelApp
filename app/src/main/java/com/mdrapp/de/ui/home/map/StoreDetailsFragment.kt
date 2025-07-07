package com.mdrapp.de.ui.home.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.BackTopBar
import com.mdrapp.de.ui.views.HorizontalTextBlock

@Composable
fun StoreDetailsFragment(
    navController: NavController,
    vm: StoreMapViewModel
) {
    BaseFragment(vm, navController) {
        val state by vm.state.collectAsState()
        StoreDetailsContent(navController, state)
    }
}

@Composable
private fun StoreDetailsContent(navController: NavController, state: StoreMapState) {
    Column(modifier = Modifier.fillMaxSize()) {
        BackTopBar(title = stringResource(R.string.dealer_map_find_a_bike)) {
            navController.popBackStack()
        }
        val context = LocalContext.current
        val mapProperties = MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
                LocalContext.current,
                R.raw.map_style
            )
        )
        val selectedLocation = state.selectedAddress
        selectedLocation?.let { location ->
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(LatLng(location.lat, location.lng), 15f)
            }
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 26.dp)
                    .height(178.dp),
                cameraPositionState = cameraPositionState,
                properties = mapProperties
            ) {
                CustomMapMarker(
                    context = context,
                    position = LatLng(location.lat,location.lng),
                    title = location.street,
                    iconResourceId = R.drawable.ic_marker_icon_black)
            }
            Text(
                text = location.name,
                style = MDRTheme.typography.title,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )

            Text(
                text = "Lorem ipsum dolor sit amet, consectetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.",
                style = MDRTheme.typography.medium,
                color = MDRTheme.colors.secondaryText,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )
            StoreDetailsHorizontalTextBlock(
                title = stringResource(R.string.dealer_map_opnening), value =
                "Mo-Fr: 09:00 - 18:00\n" + "Sa: 09:00 - 14:00\n"
            )
            Spacer(modifier = Modifier.height(16.dp))
            StoreDetailsHorizontalTextBlock(
                title = stringResource(R.string.dealer_map_location),
                value = location.street + location.zip + " " + location.city
            )
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            StoreDetailsHorizontalTextBlock(
                title = stringResource(id = R.string.telephone),
                value = location.phone
            )
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            StoreDetailsHorizontalTextBlock(
                title = stringResource(R.string.dealer_map_email),
                value = location.email
            )
            HorizontalDivider(Modifier.padding(horizontal = 16.dp))
            StoreDetailsHorizontalTextBlock(
                title = stringResource(R.string.dealer_map_website),
                value = location.website
            )
        }
    }
}

@Composable
private fun StoreDetailsHorizontalTextBlock(title: String, value: String) {
    HorizontalTextBlock(
        title = title,
        value = value,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    )
}
@Composable
fun CustomMapMarker(
    context: Context,
    position: LatLng,
    title: String,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptorFromVector(context, iconResourceId)
    Marker(
        state = rememberMarkerState(position = position),
        title = title,
        icon = icon,
    )
}

fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorResId: Int): BitmapDescriptor {
    val vectorDrawable: Drawable? = ContextCompat.getDrawable(context, vectorResId)
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
