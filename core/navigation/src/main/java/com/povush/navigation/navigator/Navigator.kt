package com.povush.navigation.navigator

import com.povush.navigation.route.api.Route

public interface Navigator {
    public val currentRoute: Route?
    public fun pop()
    public fun open(route: Route)
}
