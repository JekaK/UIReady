package com.uiready.navigation.manager

import com.uiready.navigation.NavRoute
import com.uiready.navigation.NavigationEvents
import kotlinx.coroutines.flow.Flow

/**
 * An interface defining the contract for managing UI navigation in an application.
 *
 * The implementation of this interface is responsible for coordinating navigation events, handling navigation flows,
 * and managing the navigation container.
 *
 * @property navigationFlow A [Flow] of [NavigationEvents] that represents the navigation events in the application.
 */
interface NavigationManager {
    /**
     * A [Flow] of navigation events, such as navigation back or navigating to a specific route.
     */
    val navigationFlow: Flow<NavigationEvents>

    /**
     * Navigate back to a specific route in the navigation stack.
     *
     * @param route The destination route to navigate back to.
     * @param inclusive Whether to include the specified route in the back navigation stack.
     */
    suspend fun navigateBack(
        route: NavRoute,
        inclusive: Boolean = false
    )

    /**
     * Navigate back in the navigation stack.
     *
     * @param inclusive Whether to include the current route in the back navigation stack.
     */
    suspend fun navigateBack(inclusive: Boolean = false)

    /**
     * Navigate to a specific route in the navigation stack.
     *
     * @param route The destination route to navigate to.
     * @param popUpToRoute The route to be popped off the back stack, if specified.
     * @param inclusive Whether to include the specified route in the back stack when popping.
     * @param isSingleTop Whether to create a new instance of the destination route if it already exists on the stack.
     */
    suspend fun navigateTo(
        route: NavRoute,
        popUpToRoute: NavRoute? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false
    )
}
