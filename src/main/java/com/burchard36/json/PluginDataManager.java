package com.burchard36.json;

import com.burchard36.inventory.TestConfig;
import com.burchard36.json.enums.FileFormat;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class PluginDataManager {

    public HashMap<Enum<?>, PluginDataMap> configMap = new HashMap<>();

    public PluginDataManager(final JavaPlugin plugin) {

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
    public Config getConfigFileFromMap(final Enum<?> fromMap, final Enum<?> usingKey) {
        return this.configMap.get(fromMap).getConfig(usingKey);
    }

    /**
     * Gets a PluginDataMap
     * @param toGet Enum key pointing to the PluginDataMap you want to load
     * @return instance of PluginDataMap
     */
    public PluginDataMap getDataMap(final Enum<?> toGet) {
        return this.configMap.get(toGet);
    }

    /**
     * Loads a Config file to a PluginDataMaps HashMap
     * @param toMap Enum key that points to PluginDataMan
     * @param usingKey Key to set the Config file to
     * @param config Config file class to load
     */
    public void loadConfigFileToMap(final Enum<?> toMap, final Enum<?> usingKey, final Config config) {
        this.configMap.get(toMap).loadConfigFile(usingKey, config);
    }
}
