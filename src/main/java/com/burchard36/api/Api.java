package com.burchard36.api;

import com.burchard36.api.inventory.GlobalInventoryListener;

/**
 * The interface for {@link BurchAPI}, all instances of this class must contain these methods
 *
 * @author Dalton Burchard
 * @since 2.1.5
 */
interface Api {

    /**
     * Returns whether the {@link Api} is running in Debug mode
     * @return A {@link Boolean}, true if Debug mode is active false if else
     * @since 2.1.5
     */
    boolean isDebug();

    /**
     * This is the prefix that shows before the logger
     * @return Prefix before the logger
     * @since 2.1.5
     */
    String loggerPrefix();

    /**
     * The global inventory listener for the Command API
     * @return An instance of {@link GlobalInventoryListener}
     * @since 2.1.5
     */
    GlobalInventoryListener getGlobalInventoryListener();

    /**
     * Calls after the API enables itself
     *
     * @since 2.1.5
     */
    void onPluginEnable();

    /**
     * Calls after the API disables itself
     *
     * @since 2.1.5
     */
    void onPluginDisable();

}
