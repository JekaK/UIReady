package com.uiready.navigation.manager

import com.uiready.navigation.NavContainer
import com.uiready.navigation.NavRoute
import com.uiready.navigation.NavigationEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * An implementation of the [NavigationManager] interface responsible for managing UI navigation events.
 *
 * This class handles the [Flow] of [NavigationEvents] and maintains a [NavContainer] for managing navigation routes.
 *
 * @property navigationFlow A [Flow] of [NavigationEvents] that represents the navigation events in the application.
 * @property navContainer The container that holds the defined navigation routes.
 */
class NavigationManagerImpl : NavigationManager {
    private val _navigationFlow: MutableStateFlow<NavigationEvents> =
        MutableStateFlow(NavigationEvents.Default)

    /**
     * A [Flow] of navigation events, such as navigation back or navigating to a specific route.
     */
    override val navigationFlow: Flow<NavigationEvents> = _navigationFlow

    /**
     * The container holding the defined navigation routes.
     */
    override val navContainer = NavContainer()

    /**
     * Navigate back to a specific route in the navigation stack.
     *
     * @param route The destination route to navigate back to.
     * @param inclusive Whether to include the specified route in the back navigation stack.
     */
    override suspend fun navigateBack(route: NavRoute, inclusive: Boolean) {
        _navigationFlow.value = NavigationEvents.Back(route, inclusive)
    }

    /**
     * Navigate to a specific route in the navigation stack.
     *
     * @param route The destination route to navigate to.
     * @param popUpToRoute The route to be popped off the back stack, if specified.
     * @param inclusive Whether to include the specified route in the back stack when popping.
     * @param isSingleTop Whether to create a new instance of the destination route if it already exists on the stack.
     */
    override suspend fun navigateTo(
        route: NavRoute,
        popUpToRoute: NavRoute?,
        inclusive: Boolean,
        isSingleTop: Boolean
    ) {
        _navigationFlow.value =
            NavigationEvents.NavigateTo(route, popUpToRoute?.routeName, inclusive, isSingleTop)
    }
}
