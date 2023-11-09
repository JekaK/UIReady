package com.uiready.navigation.navhost

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.uiready.navigation.manager.NavigationManager
import com.uiready.navigation.ext.subscribeToNavigationUpdates

/**
 * Composable that creates a NavHost for UI navigation using Jetpack Navigation
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
    val navController = rememberNavController()

    subscribeToNavigationUpdates(navController, navigationManager)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        contentAlignment = contentAlignment,
        route = route,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
    ) {
        builder()
    }
}