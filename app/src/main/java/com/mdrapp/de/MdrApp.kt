package com.mdrapp.de

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltAndroidApp
class MdrApp : Application() {
    @Inject @ApplicationContext lateinit var context: Context
}