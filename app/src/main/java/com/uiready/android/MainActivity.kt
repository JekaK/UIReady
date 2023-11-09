package com.uiready.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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

class MainActivity : ComponentActivity() {

    private val navigationManager: NavigationManager = NavigationManagerImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigationManager.navContainer.addRoute(NavigationRoutes.Splash)
        navigationManager.navContainer.addRoute(NavigationRoutes.Root)

        setContent {
            UIReadyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UIReadyNavHost(
                        navigationManager = navigationManager,
                        startDestination = NavigationRoutes.Splash.routeName
                    ) {
                        composable(NavigationRoutes.Splash.routeName) {
                            SplashScreen(
                                backgroundImage = R.drawable.test_background,
                                logoImage = R.drawable.ic_test_logo,
                                contentAlignment = Alignment.Center,
                                backgroundImageScale = ContentScale.FillBounds
                            )
                        }
                        composable(NavigationRoutes.Root.routeName) {

                        }
                    }
                }
            }
        }
    }
}

