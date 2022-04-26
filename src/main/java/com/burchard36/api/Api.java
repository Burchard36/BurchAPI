package com.burchard36.api;

import com.burchard36.api.inventory.GlobalInventoryListener;

public interface Api {

    /**
     * Returns whether the {@link Api} is running in Debug mode
     * @return A {@link Boolean}, true if Debug mode is active false if else
     */
    boolean isDebug();

    /**
     * This is the prefix that shows before the logger
     * @return Prefix before the logger
     */
    String loggerPrefix();

    /**
     * The global inventory listener for the Command API
     * @return An instance of {@link GlobalInventoryListener}
     */
    GlobalInventoryListener getGlobalInventoryListener();

    /**
     * Calls after the API enables itself
     */
    void onPluginEnable();

    /**
     * Calls after the API disables itself
     */
    void onPluginDisable();

}
