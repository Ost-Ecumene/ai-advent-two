package com.povush.navigation.di

import com.povush.navigation.route.api.Route

/**
 * Стандартная фабрика для создания VM с поставкой туда навигационных данных.
 */
public interface AssistedVmFactory<VM, R : Route> {
    public fun create(navData: R): VM
}
