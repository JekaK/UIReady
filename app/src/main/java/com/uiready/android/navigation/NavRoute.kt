package com.uiready.android.navigation

import androidx.navigation.NamedNavArgument
import com.uiready.navigation.NavRoute

sealed class NavigationRoutes(
    override val routeName: String,
    override val args: List<NamedNavArgument?>
) : NavRoute(routeName, args) {

    data object Splash : NavigationRoutes("Splash", listOf())

    data object Root : NavigationRoutes("Root", listOf())
}