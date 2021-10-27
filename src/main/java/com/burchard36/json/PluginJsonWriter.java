package com.burchard36.json;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginJsonWriter {

    private final JavaPlugin plugin;

    public PluginJsonWriter(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void createFile(final Config config) {
        final String configPath = config.configFilePath;
    }

}
