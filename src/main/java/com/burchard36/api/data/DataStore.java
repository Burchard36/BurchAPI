package com.burchard36.api.data;

public interface DataStore {

    /**
     * When the data store first gets initialized
     */
    void onEnable();

    /**
     * When a reload call is called on the data store
     */
    void onReload();

    /**
     *
     */
    void onDisable();

}
