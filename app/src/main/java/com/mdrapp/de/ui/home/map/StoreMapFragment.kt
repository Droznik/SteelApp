package com.mdrapp.de.ui.home.map

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.home.map.model.AddressItemVM
import com.mdrapp.de.ui.home.map.model.StoreClusterItem
import com.mdrapp.de.ui.theme.MDRTheme
import com.mdrapp.de.ui.views.PrimaryButton
import com.mdrapp.de.ui.views.PrimaryOutlinedTextField
import com.mdrapp.de.ui.views.TextSelector
import com.mdrapp.de.ui.views.TitleTopBar
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@Composable
fun StoreMapFragment(
    navController: NavController,
    vm: StoreMapViewModel
) {
    BaseFragment(vm, navController) {
        val onEvent = remember { vm::onEvent }
        val state by vm.state.collectAsState()
        StoreMapContent(onEvent, state)
    }
}

@Composable
private fun StoreMapContent(
    onEvent: (StoreMapEvent) -> Unit,
    state: StoreMapState
) {
    var selectedPin by remember { mutableStateOf<AddressItemVM?>(null) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState {
        position = state.cameraPosition
    }
    var columnScrollingEnabled by remember { mutableStateOf(true) }
    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            columnScrollingEnabled = true
        }
    }
    LaunchedEffect(state.cameraPosition,state.radarResponse) {
        cameraPositionState.position = state.cameraPosition
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TitleTopBar(title = stringResource(R.string.dealer_map_find_a_bike))
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState(), enabled = columnScrollingEnabled)
                .fillMaxSize()
        ) {
            PostCodeTextField(
                onValueChange = { onEvent(StoreMapEvent.OnPostCodeChange(it)) },
                onSubmitCode = {
                    cameraPositionState.position = state.cameraPosition
                    onEvent(StoreMapEvent.OnPostCodeSubmit)
                               },
                value = state.postCode
            )
            if (state.mapPoints.isNotEmpty()) {
                Spacer(modifier = Modifier.height(7.dp))
                TextSelector(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    items = state.mapPoints.toPersistentList(),
                    converter = { it.formattedAddress },
                    value = state.selectedMapPoint,
                ) {
                    onEvent(StoreMapEvent.OnMapPointSelected(it))
                }
            }
            Spacer(modifier = Modifier.height(11.dp))
            RadiusInputField(
                value = state.radius,
                label = {},
                onValueChange = { onEvent(StoreMapEvent.OnRadiusChange(it)) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Range",
                        tint = MDRTheme.colors.secondaryText
                    )
                }
            )
            Spacer(modifier = Modifier.height(11.dp))
            if (state.radarResponse.isNotEmpty() && !state.mapResultsError) {
                MapViewWithPins(
                    cameraPositionState,
                    state.radarResponse,
                    selectedPin,
                    onMapTouched = { columnScrollingEnabled = false },
                ) { pin ->
                    selectedPin = pin
                    coroutineScope.launch {
                        val index = state.radarResponse.indexOf(pin)
                        if (index != -1) {
                            listState.animateScrollToItem(index)
                        }
                    }
                }
                PlacesListView(
                    state.radarResponse, listState,
                    onMoreClicked = {
                        onEvent(StoreMapEvent.OnMoreClicked(it))
                    },
                ) { pin ->
                    selectedPin = pin
                }
            } else if (state.mapResultsError) {
                NotFoundMessage()
            }
            Spacer(modifier = Modifier.height(17.dp))
            ClickableImageBanner {
                onEvent(StoreMapEvent.OnBannerClick)
            }
        }
        var showDialog by remember { mutableStateOf(false) }
        if (showDialog) {
            StoreMapFaqDialog(onDismissRequest = { showDialog = false })
        }
        PrimaryButton(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = stringResource(R.string.dealer_search_faq_button_title)
        ) {
            showDialog = true
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, MapsComposeExperimentalApi::class)
@Composable
private fun MapViewWithPins(
    cameraPositionState: CameraPositionState,
    pins: List<AddressItemVM>,
    selectedPin: AddressItemVM?,
    onMapTouched: () -> Unit,
    onPinClick: (AddressItemVM) -> Unit
) {
    val mapProperties = MapProperties(
        mapStyleOptions = MapStyleOptions.loadRawResourceStyle(
            LocalContext.current,
            R.raw.map_style
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .pointerInteropFilter(
                    onTouchEvent = {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                onMapTouched()
                                false
                            }

                            else -> true
                        }
                    }
                ),
            properties = mapProperties,
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false)
        ) {
            val storeClusterItems by remember(pins) {
                mutableStateOf(
                    pins.map { pin ->
                        StoreClusterItem(
                            pin.lat,
                            pin.lng,
                            pin.name,
                            pin.street
                        )
                    }
                )
            }

            Clustering(items = storeClusterItems,
                onClusterItemClick = { item ->
                    val selectedPinItem = pins.find { it.street == item.snippetStore }
                    onPinClick(selectedPinItem!!)
                    false
                },
                clusterItemContent = { item ->
                Image(
                    painter = painterResource(id = R.drawable.ic_marker_icon_black),
                    contentDescription = "My Image",
                    modifier = Modifier.size(26.dp),
                    contentScale = ContentScale.FillHeight
                )},
                clusterContent = { cluster ->
                    ClusterContent(count = cluster.items.size)
                }
            )

            LaunchedEffect(selectedPin) {
                selectedPin?.let {
                    val position = LatLng(it.lat, it.lng)
                    cameraPositionState.animate(CameraUpdateFactory.newLatLng(position))
                }
            }
        }
    }
}

@Composable
private fun PlacesListView(
    pins: List<AddressItemVM>,
    listState: LazyListState,
    onMoreClicked: (AddressItemVM) -> Unit,
    onItemClick: (AddressItemVM) -> Unit
) {
    LazyRow(
        state = listState, modifier = Modifier
            .padding(horizontal = 13.dp)
            .padding(top = 14.dp)
            .fillMaxSize()
    ) {
        items(pins) { pin ->
            PlaceItemView(
                pin,
                onClick = { onItemClick(pin) },
                onMoreClicked = { onMoreClicked(pin) })
        }
    }
}

@Composable
private fun PlaceItemView(pin: AddressItemVM, onClick: () -> Unit, onMoreClicked: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .padding(3.dp)
            .width(342.dp)
            .height(131.dp)
            .clickable(onClick = onClick)
            .border(1.dp, Color.Black, RoundedCornerShape(8.dp)),
        color = MDRTheme.colors.primaryBackground,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = pin.name,
                style = MDRTheme.typography.semiBold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${pin.street}, ${pin.zip} ${pin.city}",
                    style = MDRTheme.typography.description,
                    color = MDRTheme.colors.secondaryText
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.dealer_map_more),
                    style = MDRTheme.typography.description,
                    modifier = Modifier.clickable {
                        onMoreClicked()
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun RadiusInputField(
    value: String,
    label: @Composable (() -> Unit),
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)
) {
    PrimaryOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        suffix = @Composable {
            Text("km")
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.NumberPassword
        ),
        singleLine = true,
        trailingIcon = trailingIcon,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
private fun ClickableImageBanner(onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = R.drawable.map_faq_banner),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 24.dp, start = 40.dp)
        ) {
            Text(
                text = stringResource(R.string.dealer_map_mein_dienstrad_shop),
                style = MDRTheme.typography.bold,
                color = MDRTheme.colors.lightText,
                fontSize = 20.sp
            )
            Text(
                text = stringResource(R.string.dealer_map_over_nine_thousand),
                style = MDRTheme.typography.regular,
                color = MDRTheme.colors.lightText,
                fontSize = 20.sp
            )
        }
        ArrowBox()
    }

}

@Composable
private fun BoxScope.ArrowBox() {
    Box(
        modifier = Modifier
            .padding(end = 38.dp, bottom = 24.dp)
            .size(49.dp)
            .align(Alignment.BottomEnd)
            .border(2.dp, MDRTheme.colors.lightText, shape = RoundedCornerShape(8.dp))
            .background(MDRTheme.colors.appGreen, shape = RoundedCornerShape(8.dp))
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowForward,
            contentDescription = null,
            tint = MDRTheme.colors.lightText,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun PostCodeTextField(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onSubmitCode: () -> Unit = {},
    value: String
) {
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
            .background(Color.Transparent),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.NumberPassword
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            onSubmitCode()
        }),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = "PLZ",
                            style = MDRTheme.typography.title,
                            color = MDRTheme.colors.primaryText,
                        )
                    }
                    innerTextField()
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_magn_glass),
                    contentDescription = "",
                    tint = MDRTheme.colors.primaryText,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 2.dp)
                        .clickable {
                            focusManager.clearFocus()
                            onSubmitCode()
                        }
                )
            }
        },
        textStyle = MDRTheme.typography.title
    )}

@Composable
private fun NotFoundMessage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 140.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_exclamation_magnifying_glass),
            contentDescription = null,
            modifier = Modifier.size(54.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.dealer_map_error_title),
            style = MDRTheme.typography.regular,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.dealer_map_error_desc),
            style = MDRTheme.typography.description,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotFoundMessage() {
    MDRTheme {
        NotFoundMessage()
    }
}

@Composable
fun ClusterContent(count: Int) {
    Box(
        modifier = Modifier.size(40.dp)
    ) {
        Canvas(modifier = Modifier.size(40.dp)) {
            drawClusterBackground(size = size)
        }
        Text(
            text = count.toString(),
            color = MDRTheme.colors.primaryBackground,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

private fun DrawScope.drawClusterBackground(size: Size) {
    drawRoundRect(
        color = Color(0xFF414141),
        size = size,
        cornerRadius = CornerRadius(20.dp.toPx(), 20.dp.toPx())
    )
}