package com.mdrapp.de.ui.util

object ClickHelper {
    private val now: Long get() = System.currentTimeMillis()
    private var lastEventTimeMs: Long = 0

    fun clickOnce(event: () -> Unit) {
        if ((now - lastEventTimeMs) >= 400L) {
            event()
        }
        lastEventTimeMs = now
    }
}