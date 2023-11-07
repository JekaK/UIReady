package com.uiready.splashscreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun SplashScreenBackground(
    modifier: Modifier = Modifier,
    @DrawableRes backgroundImage: Int,
    contentAlignment: Alignment = Alignment.Center,
    backgroundImageScale: ContentScale = ContentScale.Inside,
) {
    // Displays a splash screen with a background image.
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = contentAlignment
    ) {
        Image(
            painter = painterResource(id = backgroundImage),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = backgroundImageScale
        )
    }
}

@Composable
fun SplashScreenLogo(
    modifier: Modifier = Modifier,
    @DrawableRes logoImage: Int,
    contentAlignment: Alignment = Alignment.Center,
) {
    // Displays a splash screen with a logo image.
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = contentAlignment
    ) {
        Image(
            painter = painterResource(id = logoImage),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    @DrawableRes backgroundImage: Int? = null,
    @DrawableRes logoImage: Int? = null,
    contentAlignment: Alignment = Alignment.Center,
    backgroundImageScale: ContentScale = ContentScale.Inside,
) {
    // Displays a splash screen with a background image and/or a logo image.
    val backgroundColor = if (backgroundImage == null) Color.Black else Color.White

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = contentAlignment
    ) {
        if (backgroundImage != null) {
            Image(
                painter = painterResource(id = backgroundImage),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = backgroundImageScale
            )
        }

        if (logoImage != null) {
            Image(
                painter = painterResource(id = logoImage),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun SplashScreenWithContent(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit = {}
) {
    // Displays a splash screen with custom content.
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = contentAlignment
    ) {
        content()
    }
}
