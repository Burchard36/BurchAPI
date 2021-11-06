package com.burchard36.json;

import java.util.HashMap;

public class PluginDataMap {

    private final HashMap<Enum<?>, Config> configMap = new HashMap<>();

    public PluginDataMap() {

    }

    /**
     * Loads the Config file into the map while initializing the plugins loaf() method
     * @param E Enum to set as Key for Config
     * @param config Config to set
     */
    public void loadConfigFile(final Enum<?> E, final Config config) {
        this.configMap.putIfAbsent(E, config.load());
    }

    /**
     * Gets a
     * @param E
     * @return
     */
    public Config getConfig(final Enum<?> E) {
        return this.configMap.get(E);
    }

    /**
     * Reloads a Config file
     * @param E
     */
    public void reloadConfigFile(final Enum<?> E) {
        final Config config = this.getConfig(E);
        if (config == null) return;

    }

}
