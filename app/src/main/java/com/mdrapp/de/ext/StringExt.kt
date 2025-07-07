package com.mdrapp.de.ext

import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun Long.toDateString(format: String): String = SimpleDateFormat(format, Locale.getDefault()).format(
    Date(this)
)

fun String.formattedAsDouble(oldValue: String): String {
    return when {
        this.isEmpty() -> ""
        this.first().digitToIntOrNull() == null -> this.drop(1)
        this.toDoubleOrNull() != null -> {
            when(val dotIndex = this.indexOfFirst { it == '.' }) {
                -1 -> this.trim()
                else -> {
                    val tailSize = this.substring(dotIndex + 1).length
                    when {
                        tailSize > 2 -> this.dropLast(tailSize - 2)
                        else -> this.trim()
                    }
                }
            }
        }
        else -> oldValue
    }
}