package com.burchard36.api;

import lombok.Getter;

@Getter
public class DataStoreSettings {

    /**
     * Sets the auto save time in seconds
     */
    protected int playerDataAutoSave;

    protected DataStoreSettings() {

    }

    public DataStoreSettings setPlayerDataAutoSave(int minutes) {
        this.playerDataAutoSave = minutes;
        return this;
    }

}
