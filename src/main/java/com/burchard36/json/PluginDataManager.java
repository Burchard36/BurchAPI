package com.burchard36.json;

import com.squareup.moshi.Moshi;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class PluginDataManager {

    private final Moshi moshi;
    public HashMap<Enum<?>, PluginDataMap> dataMap = new HashMap<>();

    public PluginDataManager(final JavaPlugin plugin) {
        this.moshi = new Moshi.Builder().build();
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
     * @param usingKey Enum that hold the Config file in PluginData Map
     * @return instance of a Config file
     */
    public final JsonDataFile getDataFileFromMap(final Enum<?> fromMap, final Enum<?> usingKey) {
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
     * Loads a Data File  to a PluginDataMaps HashMap
     * @param toMap Enum key that points to PluginDataMap
     * @param usingKey Key to set the Data File to
     * @param dataFile JsonDataFile class to load
     */
    public final void loadDataFileToMap(final Enum<?> toMap, final Enum<?> usingKey, final JsonDataFile dataFile) {
        this.dataMap.get(toMap).loadDataFile(usingKey, dataFile.load());
    }

    /**
     * Loads a Data File to a PluginDataMaps HashMap
     * @param toMap String key that points to PluginDataMap
     * @param usingKey Key to set the Data File to
     * @param dataFile JsonDataFile class to load
     */
    public final void loadDataFileToMap(final Enum<?> toMap, final String usingKey, final JsonDataFile dataFile) {
        this.dataMap.get(toMap).loadDataFile(usingKey, dataFile.load());
    }

    /**
     * Save's all the DataFiles that are running on the plugin :)
     * TODO: Run this async maybe?
     */
    public final void saveAll() {
        this.dataMap.values().forEach(PluginDataMap::saveAll);
    }

    /**
     * Returns the Moshi instance
     * @return instance of Moshi
     */
    public final Moshi getMoshi() {
        return this.moshi;
    }
}
