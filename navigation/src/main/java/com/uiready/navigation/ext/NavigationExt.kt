package com.uiready.navigation.ext

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.uiready.navigation.NavigationEvents
import com.uiready.navigation.manager.NavigationManager
import kotlinx.coroutines.flow.drop

/**
 * Subscribes to navigation updates and observes changes in the [NavigationManager]'s [navigationFlow].
 * Handles navigation events, such as navigating back or to specific routes, using the provided [NavHostController].
 *
 * @param navHostController The [NavHostController] responsible for managing the navigation within the application.
 * @param navigationManager The [NavigationManager] that provides the [Flow] of [NavigationEvents].
 *
 * @see NavigationManager for managing UI navigation events.
 */
@Composable
fun subscribeToNavigationUpdates(
    navHostController: NavHostController,
    navigationManager: NavigationManager
) {
    val activity = (LocalContext.current as? Activity)
    LaunchedEffect(activity, navHostController, navigationManager.navigationFlow) {
        navigationManager.navigationFlow.drop(1).collect { intent ->
            if (activity?.isFinishing == true) return@collect
            when (intent) {
                is NavigationEvents.Back -> {
                    if (intent.route != null) {
                        navHostController.popBackStack(intent.route.routeName, intent.inclusive)
                    } else {
                        navHostController.popBackStack()
                    }
                }

                is NavigationEvents.NavigateTo -> {
                    navHostController.navigate(intent.route.routeName) {
                        launchSingleTop = intent.isSingleTop
                        intent.popUpToRoute?.let { popUpToRoute ->
                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                        }
                    }
                }

                NavigationEvents.Default -> {}
            }
        }
    }
}