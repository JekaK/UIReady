package com.uiready.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.compose.composable
import com.uiready.android.navigation.NavigationRoutes
import com.uiready.android.ui.theme.UIReadyTheme
import com.uiready.navigation.manager.NavigationManager
import com.uiready.navigation.manager.NavigationManagerImpl
import com.uiready.navigation.navhost.UIReadyNavHost
import com.uiready.splashscreen.SplashScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val navigationManager: NavigationManager = NavigationManagerImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UIReadyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val scope = rememberCoroutineScope()

                    UIReadyNavHost(
                        navigationManager = navigationManager,
                        startDestination = NavigationRoutes.Splash.routeName,
                        enterTransition = { EnterTransition.None },
                        exitTransition = { ExitTransition.None },
                        popEnterTransition = { EnterTransition.None },
                        popExitTransition = { ExitTransition.None },
                    ) {
                        composable(NavigationRoutes.Splash.routeName) {
                            SplashScreen(backgroundImage = R.drawable.test_background,
                                logoImage = R.drawable.ic_test_logo,
                                contentAlignment = Alignment.Center,
                                backgroundImageScale = ContentScale.FillBounds,
                                modifier = Modifier.clickable {
                                    scope.launch {
                                        navigationManager.navigateTo(
                                            route = NavigationRoutes.Root,
                                            isSingleTop = true,
                                            inclusive = true
                                        )
                                    }
                                })
                        }
                        composable(NavigationRoutes.Root.routeName) {
                            Column {
                                Text(text = "TEST")
                            }
                        }
                    }
                }
            }
        }
    }
}

