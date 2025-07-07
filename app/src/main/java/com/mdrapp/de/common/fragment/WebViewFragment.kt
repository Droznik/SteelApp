package com.mdrapp.de.common.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.mdrapp.de.BuildConfig
import com.mdrapp.de.data.interceptors.mdr.HEADER_AUTH
import com.mdrapp.de.data.interceptors.mdr.PREFIX_TOKEN
import com.mdrapp.de.data.storage.ACCESS_TOKEN
import com.mdrapp.de.di.sessionDataStore
import com.mdrapp.de.navigation.HomeNavHost
import com.mdrapp.de.ui.views.Loader
import kotlinx.coroutines.flow.first

enum class WebViewUrls(val value: String) {
    CALCULATOR("${BuildConfig.API_BASE_URL}redirect/customerhub/leasing-calculator?customerapp=true"),
    BACK("mdrapp://internal-browser.dismiss")
}

@Composable
@SuppressLint("SetJavaScriptEnabled")
fun WebViewFragment(
    navController: NavController,
    url: String,
    modifier: Modifier = Modifier
) {
    val appContext = LocalContext.current
    var token by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    val fileChooser = remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }
    val activityResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val uri = data?.data
            fileChooser.value?.onReceiveValue(arrayOf(uri ?: Uri.EMPTY))
            fileChooser.value = null
        }
    val sessionStorage = appContext.sessionDataStore

    LaunchedEffect(Unit) {
        try {
            token = sessionStorage.data.first()[ACCESS_TOKEN].orEmpty()
        } catch (e: Exception) {
            Firebase.crashlytics.recordException(e)
            e.printStackTrace()
        }
    }

    if (token.isNotEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RectangleShape)
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true

                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(
                                view: WebView?,
                                request: WebResourceRequest?
                            ): Boolean {
                                request?.url?.toString()?.let { url ->
                                    when {
                                        url == WebViewUrls.BACK.value -> {
                                            navController.popBackStack()
                                            return true
                                        }

                                        url.startsWith("cbsapp://") -> {
                                            handleCustomUrlScheme(navController, url)
                                            return true
                                        }

                                        else -> return false
                                    }
                                }
                                return false
                            }

                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                super.onPageStarted(view, url, favicon)
                                loading = true
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                loading = false
                            }
                        }

                        webChromeClient = object : WebChromeClient() {
                            override fun onShowFileChooser(
                                webView: WebView?,
                                filePathCallback: ValueCallback<Array<Uri>>?,
                                fileChooserParams: FileChooserParams?
                            ): Boolean {
                                fileChooser.value = filePathCallback
                                val intent = Intent(Intent.ACTION_GET_CONTENT)
                                intent.type = "*/*"
                                activityResultLauncher.launch(intent)
                                return true
                            }

                            override fun onConsoleMessage(message: ConsoleMessage): Boolean {
                                if (BuildConfig.DEBUG) {
                                    Log.d(
                                        "WebChromeClient", "${message.message()} -- From line " +
                                                "${message.lineNumber()} of ${message.sourceId()}"
                                    )
                                }
                                return true
                            }
                        }

                        settings.loadWithOverviewMode = true
                        settings.useWideViewPort = true
                        settings.setSupportZoom(false)
                        settings.allowFileAccess = true
                        settings.allowContentAccess = true
                    }
                },
                update = { webView ->
                    webView.loadUrl(
                        url,
                        mapOf(HEADER_AUTH to "$PREFIX_TOKEN $token")
                    )
                }
            )

            DisposableEffect(activityResultLauncher) {
                onDispose {
                    fileChooser.value?.onReceiveValue(arrayOf(Uri.EMPTY))
                    fileChooser.value = null
                }
            }

            if (loading) {
                Loader()
            }
        }
    }
}

private fun handleCustomUrlScheme(navController: NavController, url: String) {
    when {
        url.startsWith("cbsapp://internal-browser.page.faq") -> {
            navController.navigate(HomeNavHost.Faq.route)
        }
    }
}