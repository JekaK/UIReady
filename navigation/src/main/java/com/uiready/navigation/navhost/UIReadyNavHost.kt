package com.uiready.navigation.navhost

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.uiready.navigation.ext.subscribeToNavigationUpdates
import com.uiready.navigation.manager.NavigationManager
import kotlinx.coroutines.launch

/**
 * Composable that creates a NavHost for UI navigation using Jetpack Navigation.
 * This composable simplifies the setup of a navigation graph and integrates with the
 * [NavigationManager] to handle navigation events.
 *
 * @param navigationManager The [NavigationManager] responsible for managing navigation events.
 * @param startDestination The starting destination route for the NavHost.
 * @param modifier The modifier to be applied to the NavHost.
 * @param contentAlignment The alignment of the content inside the NavHost.
 * @param route The route associated with this NavHost. If null, the route is determined automatically.
 * @param enterTransition The enter transition for route changes in the NavHost.
 * @param exitTransition The exit transition for route changes in the NavHost.
 * @param popEnterTransition The enter transition for pop operations (e.g., back navigation).
 * @param popExitTransition The exit transition for pop operations (e.g., back navigation).
 * @param builder A lambda that configures the navigation graph by adding composable destinations.
 *
 * @see subscribeToNavigationUpdates for handling navigation events.
 */
@Composable
fun UIReadyNavHost(
    navigationManager: NavigationManager,
    startDestination: String,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    route: String? = null,
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        { fadeIn(animationSpec = tween(700)) },
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        { fadeOut(animationSpec = tween(700)) },
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition) =
        enterTransition,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition) =
        exitTransition,
    builder: NavGraphBuilder.() -> Unit
) {
    // Create a NavController and a CoroutineScope
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    // Subscribe to navigation updates from the NavigationManager
    navController.subscribeToNavigationUpdates(navigationManager)

    // Handle back navigation with a BackHandler tied to the lifecycle
    BackHandlerWithLifecycle {
        scope.launch {
            navigationManager.navigateBack()
        }
    }

    // Create the NavHost with specified parameters
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        contentAlignment = contentAlignment,
        route = route,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) {
        builder()
    }
}

/**
 * Composable that wraps a BackHandler and observes the lifecycle events to control its behavior.
 *
 * @param enabled Whether the BackHandler should be enabled.
 * @param onBack Callback function to be invoked when the back action is triggered.
 */
@Composable
fun BackHandlerWithLifecycle(
    enabled: Boolean = true,
    onBack: () -> Unit
) {
    // State to refresh the BackHandler when the lifecycle state changes
    var refreshBackHandler by rememberSaveable { mutableStateOf(false) }

    // Observe lifecycle events and update the refreshBackHandler state accordingly
    LifecycleEventObserver { _, event ->
        when (event) {
            Lifecycle.Event.ON_PAUSE -> refreshBackHandler = false
            Lifecycle.Event.ON_RESUME -> refreshBackHandler = true
            else -> Unit
        }
    }

    // If refreshBackHandler is true, render the BackHandler
    if (refreshBackHandler) {
        BackHandler(enabled = enabled) {
            onBack()
        }
    }
}
