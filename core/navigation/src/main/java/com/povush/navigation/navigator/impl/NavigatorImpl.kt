package com.povush.navigation.navigator.impl

import androidx.navigation3.runtime.NavBackStack
import com.povush.navigation.navigator.Navigator
import com.povush.navigation.route.api.Route

internal class NavigatorImpl(
    private val backStack: NavBackStack
) : Navigator {

    override val currentRoute: Route? get() = backStack.last() as? Route

    override fun pop() {
        backStack.removeLastOrNull()
    }

    override fun open(route: Route) {
        backStack.add(route)
    }
}
