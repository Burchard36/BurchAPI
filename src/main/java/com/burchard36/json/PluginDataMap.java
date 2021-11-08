package com.burchard36.json;

import java.util.HashMap;

public class PluginDataMap {

    private final HashMap<Enum<?>, JsonDataFile> dataMap = new HashMap<>();

    public PluginDataMap() {

    }

    /**
     * Loads the Config file into the map while initializing the plugins loaf() method
     * @param E Enum to set as Key for Config
     * @param config Config to set
     */
    public PluginDataMap loadDataFile(final Enum<?> E, final JsonDataFile config) {
        this.dataMap.putIfAbsent(E, config.load());
        return this;
    }

    /**
     * Gets a JsonDataFile thats cached into HashMap
     * @param E Enum field to use
     * @return JsonDataFile from HashMap
     */
    public JsonDataFile getDataFile(final Enum<?> E) {
        return this.dataMap.get(E);
    }

    /**
     * Reloads a Config file
     * @param E Enum class field to use
     */
    public void reloadDataFile(final Enum<?> E) {
        final JsonDataFile config = this.getDataFile(E);
        if (config == null) return;
        config.save();
        config.load();
    }
}
