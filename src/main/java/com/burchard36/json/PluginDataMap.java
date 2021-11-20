package com.burchard36.json;

import java.util.HashMap;

public class PluginDataMap {

    private final HashMap<Enum<?>, JsonDataFile> dataMap;
    private final HashMap<String, JsonDataFile> dataMapByStrings;

    public PluginDataMap() {
        this.dataMap = new HashMap<>();
        this.dataMapByStrings = new HashMap<>();
    }

    /**
     * Loads the Config file into the map while initializing the plugins loaf() method
     * @param E Enum to set as Key for Config
     * @param config Config to set
     */
    public PluginDataMap loadDataFile(final Enum<?> E, final JsonDataFile config) {
        this.dataMap.putIfAbsent(E, config);
        return this;
    }

    public PluginDataMap loadDataFile(final String E, final JsonDataFile dataFile) {
        this.dataMapByStrings.putIfAbsent(E, dataFile);
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
     * Gets data from the map by a string hats cached
     * @param E String key to find the data by
     * @return JsonDataFile instance
     */
    public JsonDataFile getDataFile(final String E) {
        return this.dataMapByStrings.get(E);
    }

    /**
     * Returns the DataMap that organized by Enum objects
     * @return HashMap of JsonDataFile's that were loaded by Enums
     */
    public final HashMap<Enum<?>, JsonDataFile> getDataMap() {
        return this.dataMap;
    }

    /**
     * Returns the DataMap tht organized by String objects
     * @return HashMap of jsonDataFile's that were loaded by strings
     */
    public HashMap<String, JsonDataFile> getDataMapByStrings() {
        return this.dataMapByStrings;
    }

    /**
     * Saves all the objects inside the HashMaps
     */
    public final void saveAll() {
        //this.getDataMap().values().forEach(JsonDataFile::save);
        //this.getDataMapByStrings().values().forEach(JsonDataFile::save);
    }

    /**
     * Reloads a Config file
     * @param E Enum class field to use
     */
    public void reloadDataFile(final Enum<?> E) {
        final JsonDataFile dataFile = this.getDataFile(E);
        if (dataFile == null) return;
    }

    /**
     * Reloads a config file
     * @param E String object to use
     */
    public void reloadDataFile(final String E) {
        final JsonDataFile dataFile = this.getDataFile(E);
        if (dataFile == null) return;
    }
}
