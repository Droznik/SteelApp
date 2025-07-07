package com.mdrapp.de.ui.util

import android.content.Context
import android.net.Uri
import android.util.Base64
import com.mdrapp.de.ext.getOriginalFileName
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FileHelper @Inject constructor(@ApplicationContext private val  appContext: Context) {

    fun getPDFBase64(uri: Uri): String {
        val byteArray = appContext.contentResolver.openInputStream(uri)?.use { it.buffered().readBytes() }

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun getOriginalFileName(uri: Uri): String = uri.getOriginalFileName(appContext) ?: System.currentTimeMillis().toString()
}