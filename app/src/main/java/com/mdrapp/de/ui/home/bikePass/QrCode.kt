package com.mdrapp.de.ui.home.bikePass

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

@Composable
fun QrCode(
    value: String,
    modifier: Modifier
) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(value) {
        try {
            bitmap = BarcodeEncoder().encodeBitmap(value, BarcodeFormat.QR_CODE, 512, 512)
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            e.printStackTrace()
        }
    }

    bitmap?.let {
        AsyncImage(
            model = it,
            contentDescription = null,
            modifier = modifier
        )
    }
}