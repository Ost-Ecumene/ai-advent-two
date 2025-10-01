package com.povush.navigation.navigator.impl

import com.povush.navigation.navigator.Navigator
import com.povush.navigation.route.api.Route

class MockNavigatorImpl() : Navigator {

    override val currentRoute: Route? get() = null

    override fun pop() {}
    override fun open(route: Route) {}
}
