package com.burchard36.json;

import com.squareup.moshi.Moshi;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class PluginDataManager {

    private final Moshi moshi;
    public HashMap<Enum<?>, PluginDataMap> configMap = new HashMap<>();

    public PluginDataManager(final JavaPlugin plugin) {
        this.moshi = new Moshi.Builder().build();
    }

    public void registerPluginMap(final Enum<?> E, PluginDataMap map) {
        this.configMap.putIfAbsent(E, map);
    }

    /**
     * Grabs a Config file from a Map
     * @param fromMap Enum that's holding he PluginDataMap
     * @param usingKey Enum that hold the Config file in PluginData Map
     * @return instance of a Config file
     */
    public JsonDataFile getConfigFileFromMap(final Enum<?> fromMap, final Enum<?> usingKey) {
        return this.configMap.get(fromMap).getDataFile(usingKey);
    }

    /**
     * Gets a PluginDataMap
     * @param toGet Enum key pointing to the PluginDataMap you want to load
     * @return instance of PluginDataMap
     */
    public final PluginDataMap getDataMap(final Enum<?> toGet) {
        return this.configMap.get(toGet);
    }

    /**
     * Clears a plugins DataMap, typically used when reloading the class holding the DataMap
     * @param toClear
     */
    public final void clearDataMap(final Enum<?> toClear) {
        this.configMap.remove(toClear);
    }

    /**
     * Loads a Config file to a PluginDataMaps HashMap
     * @param toMap Enum key that points to PluginDataMan
     * @param usingKey Key to set the Config file to
     * @param config Config file class to load
     */
    public void loadConfigFileToMap(final Enum<?> toMap, final Enum<?> usingKey, final JsonDataFile config) {
        this.configMap.get(toMap).loadDataFile(usingKey, config.load());
    }

    /**
     * Returns the Moshi instance
     * @return instance of Moshi
     */
    public final Moshi getMoshi() {
        return this.moshi;
    }
}
