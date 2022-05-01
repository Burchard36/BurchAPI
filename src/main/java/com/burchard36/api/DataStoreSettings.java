package com.burchard36.api;

import lombok.Getter;

@Getter
public class DataStoreSettings {

    /**
     * Sets the auto save time in seconds
     */
    protected int playerDataAutoSave;
    protected final ApiSettings settings;

    protected DataStoreSettings(final ApiSettings settings) {
        this.settings = settings;
    }

    public DataStoreSettings setPlayerDataAutoSave(int minutes) {
        this.playerDataAutoSave = minutes;
        return this;
    }

    /**
     * Joins back to {@link ApiSettings} for easier chaining
     * @return Instance of {@link ApiSettings} for easier chaining
     * @since 2.1.8
     */
    public ApiSettings join() {
        return this.settings;
    }

}
