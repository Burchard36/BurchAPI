package com.burchard36.json;

import java.util.HashMap;

public class PluginDataMap {

    private final HashMap<String, JsonDataFile> dataMapByStrings;
    private final PluginJsonWriter writer;

    public PluginDataMap(final PluginJsonWriter writer) {
        this.writer = writer;
        this.dataMapByStrings = new HashMap<>();
    }

    /**
     * Loads the Config file into the map while loading its data from file
     * @param E String to set as Key for Config
     * @param dataFile JsonDataFile to set
     */
    public void loadDataFile(final String E, final JsonDataFile dataFile) {
        this.dataMapByStrings.putIfAbsent(E, this.writer.getDataFromFile(dataFile));
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
        this.getDataMapByStrings().values().forEach(this.writer::writeDataToFile);
    }

    /**
     * Reloads a config file
     * @param E String object to use
     */
    public void reloadDataFile(final String E) {
        final JsonDataFile dataFile = this.getDataFile(E);
        if (dataFile == null) return;
        this.getDataMapByStrings().replace(E, this.reload(dataFile));
    }

    private JsonDataFile reload(final JsonDataFile dataFile) {
        this.writer.writeDataToFile(dataFile);
        return this.writer.getDataFromFile(dataFile);
    }
}
