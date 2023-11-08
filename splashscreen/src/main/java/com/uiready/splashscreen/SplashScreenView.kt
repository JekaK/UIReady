package com.uiready.splashscreen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

/**
 * Default values for [SplashScreen].
 */
object SplashScreenDefaults {
    /** Default content scale for background images. */
    val defaultContentScale = ContentScale.Inside
}

/**
 * Composable that displays a splash screen with a background image and an optional icon image.
 *
 * @param modifier The modifier to be applied to the [SplashScreenBackground] composable.
 * @param backgroundImage The resource ID of the background image.
 * @param icon The resource ID of the optional icon image.
 * @param contentAlignment The alignment of the content within the [SplashScreenBackground].
 * @param backgroundImageScale The scale mode for the background image.
 * @param iconModifier The modifier to be applied to the icon image.
 */
@Composable
private fun SplashScreenBackground(
    modifier: Modifier = Modifier,
    @DrawableRes backgroundImage: Int,
    @DrawableRes icon: Int? = null,
    contentAlignment: Alignment = Alignment.Center,
    backgroundImageScale: ContentScale = SplashScreenDefaults.defaultContentScale,
    iconModifier: Modifier = Modifier
) {
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

    if (icon != null) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = iconModifier
        )
    }
}

/**
 * Composable that displays a splash screen with a logo image.
 *
 * @param modifier The modifier to be applied to the [SplashScreenLogo] composable.
 * @param logoImage The resource ID of the logo image.
 * @param contentAlignment The alignment of the content within the [SplashScreenLogo].
 * @param logoModifier The modifier to be applied to the logo image.
 */
@Composable
private fun SplashScreenLogo(
    modifier: Modifier = Modifier,
    @DrawableRes logoImage: Int,
    contentAlignment: Alignment = Alignment.Center,
    logoModifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = contentAlignment
    ) {
        Image(
            painter = painterResource(id = logoImage),
            contentDescription = null,
            modifier = logoModifier.fillMaxWidth()
        )
    }
}

/**
 * Composable that displays a splash screen with custom content.
 *
 * @param modifier The modifier to be applied to the [SplashScreenWithContent] composable.
 * @param contentAlignment The alignment of the custom content within the [SplashScreenWithContent].
 * @param content The custom content to display.
 */
@Composable
private fun SplashScreenWithContent(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = contentAlignment
    ) {
        content()
    }
}

/**
 * Composable that displays a splash screen with options for a background image, logo image, or custom content.
 *
 * @param modifier The modifier to be applied to the [SplashScreen] composable.
 * @param backgroundImage The resource ID of the background image (optional).
 * @param logoImage The resource ID of the logo image (optional).
 * @param contentAlignment The alignment of the content within the [SplashScreen].
 * @param backgroundImageScale The scale mode for the background image.
 * @param iconModifier The modifier to be applied to the icon image (if used).
 * @param content The custom content to display (if background and logo images are not provided).
 */
@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    @DrawableRes backgroundImage: Int? = null,
    @DrawableRes logoImage: Int? = null,
    contentAlignment: Alignment = Alignment.Center,
    backgroundImageScale: ContentScale = SplashScreenDefaults.defaultContentScale,
    iconModifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = contentAlignment
    ) {
        backgroundImage?.let {
            SplashScreenBackground(
                modifier = Modifier.fillMaxSize(),
                backgroundImage = it,
                contentAlignment = contentAlignment,
                backgroundImageScale = backgroundImageScale,
                icon = logoImage,
                iconModifier = iconModifier
            )
        } ?: logoImage?.let {
            SplashScreenLogo(
                modifier = Modifier.fillMaxSize(),
                logoImage = it,
                contentAlignment = contentAlignment,
                logoModifier = iconModifier
            )
        } ?: SplashScreenWithContent(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = contentAlignment,
            content = content
        )
    }
}