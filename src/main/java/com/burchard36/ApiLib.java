package com.burchard36;

import com.burchard36.json.PluginDataManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class ApiLib implements Api {

    public JavaPlugin plugin;
    private PluginDataManager manager;
    public ApiLib lib;

    /**
     * Initializes the API to a plugin
     */
    public ApiLib initializeApi(final JavaPlugin plugin) {
        this.plugin = plugin;
        this.manager = new PluginDataManager(plugin);
        return this;
    }

    public PluginDataManager getDataManager() {
        return this.manager;
    }

    @Override
    public PluginDataManager getPluginDataManager() {
        return this.lib.getPluginDataManager();
    }
}
