package com.burchard36.api.data;

public interface DataStore {

    /**
     * When the data store first gets initialized
     */
    void onEnable();

    /**
     *
     */
    void onDisable();

}
