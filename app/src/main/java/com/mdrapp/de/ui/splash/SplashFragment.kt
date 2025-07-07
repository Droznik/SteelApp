package com.mdrapp.de.ui.splash

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import com.mdrapp.de.R
import com.mdrapp.de.common.fragment.BaseFragment
import com.mdrapp.de.ui.theme.MDRTheme
import kotlinx.coroutines.delay


@Composable
fun SplashFragment(navController: NavController, vm: SplashViewModel) {
    BaseFragment(vm, navController) {
        HideSystemsBars()
        val onEvent = remember { vm::onEvent }

        SplashContent(onEvent)
    }
}

@Composable
fun SplashContent(onEvent: (SplashEvent) -> Unit) {
    Image(
        painter = painterResource(R.drawable.img_splash_bg),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ){
        val boxScope = this
        val density = LocalDensity.current
        val animationDuration = 500
        val titleWidth = 312.dp
        val defaultPadding = 39.dp
        val resultPadding = remember {
            when {
                boxScope.maxWidth >= titleWidth + (defaultPadding * 2) -> (boxScope.maxWidth - titleWidth) / 2
                else -> defaultPadding
            }
        }
        val startSize = 160.dp
        val targetSize = 70.dp
        val startX = remember { density.run { ((boxScope.maxWidth / 2) - (startSize / 2)).toPx() } }
        val startY = remember { density.run { ((boxScope.maxHeight / 2) - (startSize / 2)).toPx() } }
        val targetX = remember { density.run { (boxScope.maxWidth - resultPadding - targetSize).toPx() } }
        val targetY = remember { density.run { 240.dp.toPx() } }
        var size by remember { mutableStateOf(startSize) }
        var offset by remember { mutableStateOf(Offset(startX, startY)) }
        val translationAnim by animateOffsetAsState(
            targetValue = offset,
            label = "",
            animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing),
        )
        val sizeAnim by animateDpAsState(
            targetValue = size,
            label = "",
            animationSpec = tween(durationMillis = animationDuration, easing = LinearEasing),
        )
        var showTitle by remember { mutableStateOf(false) }
        var showButton by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(1000)
            showTitle = true
            offset = Offset(targetX, targetY)
            size = targetSize
            delay(animationDuration.toLong() + 100)
            showButton = true
            delay(animationDuration.toLong() + 100)
            onEvent(SplashEvent.Next)
        }

        AnimatedVisibility(
            visible = showTitle,
            enter = scaleIn(
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = LinearEasing
                )
            ),
            modifier = Modifier
                .padding(top = 116.dp)
                .padding(horizontal = resultPadding)
                .fillMaxWidth()
                .height(120.dp)
        ){
            Box(
                contentAlignment = Alignment.BottomCenter
            ){
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.img_splash_title),
                    contentDescription = null,
                    tint = MDRTheme.colors.lightText,
                )
            }
        }
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_menu_home),
            contentDescription = null,
            tint = MDRTheme.colors.lightText,
            modifier = Modifier
                .size(sizeAnim)
                .graphicsLayer(
                    translationX = translationAnim.x,
                    translationY = translationAnim.y
                )
        )

        AnimatedVisibility(
            visible = showButton,
            enter = slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(
                    durationMillis = animationDuration,
                    easing = LinearEasing
                )
            ),
            modifier = Modifier
                .padding(horizontal = resultPadding)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ){
            Column(
                modifier = Modifier.fillMaxWidth().padding(bottom = 81.dp)
            ) {
                Text(
                    text = stringResource(R.string.splash_description),
                    style = MDRTheme.typography.medium,
                    fontSize = 18.sp,
                    color = MDRTheme.colors.lightText,
                    textAlign = TextAlign.Center
                )
//                Spacer(modifier = Modifier.height(26.dp))
//                PrimaryButton(text = stringResource(R.string.splash_register_now)) {
//
//                }
            }
        }
    }
}

@Composable
fun HideSystemsBars() {
    val view = LocalView.current

    DisposableEffect(Unit) {
        val window = (view.context as Activity).window
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)

        insetsController.apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        onDispose {
            insetsController.apply {
                show(WindowInsetsCompat.Type.systemBars())
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
            }
        }
    }
}

@Preview
@Composable
private fun SplashPreview() {
    MDRTheme {
        Box {
            SplashContent(onEvent = {})
        }
    }
}