package com.uiready.navigation.ext

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.uiready.navigation.NavRoute
import com.uiready.navigation.NavigationEvents
import com.uiready.navigation.manager.NavigationManager
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch

/**
 * Subscribes to navigation updates and observes changes in the [NavigationManager]'s [navigationFlow].
 * Handles navigation events, such as navigating back or to specific routes, using the provided [NavHostController].
 *
 * @param navigationManager The [NavigationManager] that provides the [Flow] of [NavigationEvents].
 *
 * @see NavigationManager for managing UI navigation events.
 */
@Composable
fun NavHostController.subscribeToNavigationUpdates(
    navigationManager: NavigationManager
) {
    // Retrieve the CoroutineScope and LifecycleOwner from the Compose context
    val scope = rememberCoroutineScope()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val activity = (LocalContext.current as? Activity)

    // Create a DisposableEffect to observe changes in the navigationFlow
    DisposableEffect(activity, this, navigationManager.navigationFlow) {
        // Define a LifecycleEventObserver to handle events, particularly ON_CREATE
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                // Launch a coroutine to collect and process navigation events
                scope.launch {
                    snapshotFlow {
                        navigationManager.navigationFlow
                    }.collect { state ->
                        state.collect { intent ->
                            if (activity?.isFinishing == true) return@collect
                            // Process the collected navigation events
                            when (intent) {
                                is NavigationEvents.Back -> {
                                    if (intent.route != null) {
                                        // Handle Back navigation with or without a specified route
                                        this@subscribeToNavigationUpdates.popBackStack(
                                            intent.route.routeName,
                                            intent.inclusive
                                        )
                                    } else {
                                        // Handle general Back navigation
                                        this@subscribeToNavigationUpdates.popBackStack()
                                    }
                                }

                                is NavigationEvents.NavigateTo -> {
                                    // Handle navigation to a specific route
                                    this@subscribeToNavigationUpdates.navigate(intent.route.routeName) {
                                        launchSingleTop = intent.isSingleTop
                                        intent.popUpToRoute?.let { popUpToRoute ->
                                            popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                                        }
                                    }
                                }

                                NavigationEvents.Default -> {
                                    // Handle default navigation event (if any)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}