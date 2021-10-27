package com.burchard36.json;

import java.util.HashMap;

public class PluginDataMap {

    private final HashMap<Enum<?>, Config> configMap = new HashMap<>();

    public PluginDataMap() {

    }

    public void loadConfigFile(final Enum<?> E, final Config config) {
        this.configMap.putIfAbsent(E, config.onLoad());
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
