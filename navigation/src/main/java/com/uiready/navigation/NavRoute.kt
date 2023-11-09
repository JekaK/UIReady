package com.uiready.navigation

import androidx.navigation.NamedNavArgument

/**
 * Represents a navigation route within the application. Subclasses can define specific route details.
 *
 * @property routeName The unique name of the navigation route.
 * @property args The list of named navigation arguments associated with the route, if any.
 */
open class NavRoute(open val routeName: String, open val args: List<NamedNavArgument?>)

/**
 * A container for managing navigation routes in the application.
 *
 * @property _navigationRoutesList The internal mutable list that stores navigation routes.
 * @property navigationRoutesList An immutable list of navigation routes exposed for reading.
 */
class NavContainer {
    private val _navigationRoutesList: MutableList<NavRoute> = mutableListOf()

    /**
     * Retrieves an immutable list of navigation routes for reading purposes.
     */
    val navigationRoutesList: List<NavRoute> = _navigationRoutesList

    /**
     * Adds a navigation route to the container.
     *
     * @param route The navigation route to be added.
     */
    fun addRoute(route: NavRoute) {
        _navigationRoutesList.add(route)
    }

    /**
     * Removes a navigation route from the container.
     *
     * @param route The navigation route to be removed.
     */
    fun removeRoute(route: NavRoute) {
        _navigationRoutesList.remove(route)
    }
}

/**
 * Sealed class representing different types of navigation events in the application.
 */
sealed class NavigationEvents {
    /**
     * Represents a "Back" navigation event, indicating a request to navigate back in the application.
     *
     * @property route The specific route to navigate back to, if provided.
     * @property inclusive Whether to include the specified route in the back navigation stack.
     */
    data class Back(
        val route: NavRoute? = null,
        val inclusive: Boolean = false,
    ) : NavigationEvents()

    /**
     * Represents a "NavigateTo" navigation event, indicating a request to navigate to a specific route.
     *
     * @property route The destination route to navigate to.
     * @property popUpToRoute The route to be popped off the back stack, if specified.
     * @property inclusive Whether to include the specified route in the back stack when popping.
     * @property isSingleTop Whether to create a new instance of the destination route if it already exists on the stack.
     */
    data class NavigateTo(
        val route: NavRoute,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
    ) : NavigationEvents()

    /**
     * Represents the default or no-op navigation event.
     */
    data object Default : NavigationEvents()
}
