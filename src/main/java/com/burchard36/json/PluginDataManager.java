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

    }

    /**
     * Grabs a Config file from a Map
     * @param fromMap Enum that's holding he PluginDataMap
     * @param usingKey Enum that hold the Config file in PluginData Map
     * @return
     */
    public Config getConfigFileFromMap(final Enum<?> fromMap, final Enum<?> usingKey) {
        return this.configMap.get(fromMap).getConfig(usingKey);
    }
}
