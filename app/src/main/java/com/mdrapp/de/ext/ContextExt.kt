package com.mdrapp.de.ext

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.mdrapp.de.ui.util.UIText
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


fun Context.toast(message: UIText, length: Int = Toast.LENGTH_LONG): Toast = Toast
    .makeText(this, message.asString(this), length)
    .apply { show() }

fun Context.shareBitmap(bitmap: Bitmap) {
    try {
        val cachePath = File(cacheDir, "images")
        cachePath.mkdirs()
        val stream = FileOutputStream("$cachePath/image.jpg")
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        stream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    val imagePath = File(cacheDir, "images")
    val newFile = File(imagePath, "image.jpg")
    val contentUri = FileProvider.getUriForFile(
        this,
        "com.mdrapp.de.fileprovider",
        newFile
    )
    if (contentUri != null) {
        val shareIntent = Intent()
        shareIntent.setAction(Intent.ACTION_SEND)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        shareIntent.setDataAndType(
            contentUri,
            contentResolver.getType(contentUri)
        )
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        startActivity(Intent.createChooser(shareIntent, ""))
    }
}

fun Context.saveBitmapToGallery(bitmap: Bitmap) {
    val filename = "QRCode_${System.currentTimeMillis()}.jpg"
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    val uri: Uri? = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    if (uri != null) {
        try {
            val stream = contentResolver.openOutputStream(uri)
            if (stream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
            }
            stream?.close()
            Toast.makeText(this, "Image saved to Gallery", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
        }
    }
}

fun Context.browse(url: String, vararg urls: String) {
    val intents = mutableListOf<Intent>()
    val urlIntent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(url) }

    urlIntent.resolveActivity(packageManager)?.let { intents.add(urlIntent) }
    for (u in urls) {
        val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(u) }
        intent.resolveActivity(packageManager)?.let { intents.add(intent) }
    }
    if (intents.isNotEmpty()) {
        when(intents.size) {
            1 -> startActivity(intents[0])
            else -> {
                val chooserIntent = Intent.createChooser(intents[0], null)
                chooserIntent.putExtra(
                    Intent.EXTRA_INITIAL_INTENTS,
                    intents.subList(1, intents.size).toTypedArray()
                )
                startActivity(chooserIntent)
            }
        }
    }
}

fun Context.sendEmail(email: String = "customercare@baronmobil.com", subject: String = "", body: String = "") {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }
    try {
        startActivity(Intent.createChooser(intent, "Send Email"))
    } catch (e: Exception) {
        Firebase.crashlytics.recordException(e)
        e.printStackTrace()
    }
}

fun Context.makeCall(number: String) {
    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
    callIntent.resolveActivity(packageManager)?.let { startActivity(callIntent) }
}

