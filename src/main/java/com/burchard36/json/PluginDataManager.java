package com.burchard36.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class PluginDataManager {

    private final ObjectMapper mapper;
    private final PluginJsonWriter jsonWriter;
    public HashMap<Enum<?>, PluginDataMap> dataMap = new HashMap<>();

    public PluginDataManager(final JavaPlugin plugin) {
        this.mapper = new ObjectMapper();
        this.jsonWriter = new PluginJsonWriter(plugin);
    }

    /**
     * Registers a new Plugin map for a plugin or system to use
     * @param E Enum key for the PluginDataMap
     * @param map PluginDataMap instance, typically a new instance
     */
    public final void registerPluginMap(final Enum<?> E, PluginDataMap map) {
        this.dataMap.putIfAbsent(E, map);
    }

    /**
     * Grabs a Config file from a Map
     * @param fromMap Enum that's holding he PluginDataMap
     * @param usingKey String that hold the Config file in PluginData Map
     * @return instance of a Config file
     */
    public final JsonDataFile getDataFileFromMap(final Enum<?> fromMap, final String usingKey) {
        return this.dataMap.get(fromMap).getDataFile(usingKey);
    }

    /**
     * Gets a PluginDataMap
     * @param toGet Enum key pointing to the PluginDataMap you want to load
     * @return instance of PluginDataMap
     */
    public final PluginDataMap getDataMap(final Enum<?> toGet) {
        return this.dataMap.get(toGet);
    }

    /**
     * Clears a plugins DataMap, typically used when reloading the class holding the DataMap
     * @param toClear Enum key of PluginDataMap to clear
     */
    public final void clearDataMap(final Enum<?> toClear) {
        this.dataMap.remove(toClear);
    }

    /**
     * Loads a Data File to a PluginDataMaps HashMap
     * @param toMap String key that points to PluginDataMap
     * @param usingKey Key to set the Data File to
     * @param dataFile JsonDataFile class to load
     */
    public final void loadDataFileToMap(final Enum<?> toMap, final String usingKey, final JsonDataFile dataFile) {
        this.dataMap.get(toMap).loadDataFile(usingKey, this.jsonWriter.getDataFromFile(dataFile));
    }

    /**
     * Clears a data map from memory by Enum value
     * @param E Enum key of PluginDataMap to clear
     */
    public final void clearMap(final Enum<?> E) {
        this.dataMap.remove(E);
    }

    /**
     * Save's all the DataFiles that are running on the plugin :)
     * TODO: Run this async maybe?
     */
    public final void saveAll() {
        this.dataMap.values().forEach(PluginDataMap::saveAll);
    }

    /**
     * Returns the Jackson instance
     * @return instance of Jackson
     */
    public final ObjectMapper getMapper() {
        return this.mapper;
    }

    /**
     * Returns the PluginJsonWriter instance for writing data to Json files
     * @return instance of PluginJsonWriter
     */
    public final PluginJsonWriter getJsonWriter() {
        return this.jsonWriter;
    }
}
