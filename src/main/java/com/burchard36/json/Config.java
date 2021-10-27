package com.burchard36.json;

import com.burchard36.Api;
import com.burchard36.json.enums.FileFormat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Config extends ConfigFile {

    public String configFilePath;
    public JavaPlugin plugin;
    public Api api;
    public FileFormat format;
    public PluginDataManager manager;


    public Config(final JavaPlugin plugin,
                  final String pathToFile,
                  final FileFormat format) {
        this.plugin = plugin;
        this.configFilePath = pathToFile;
        this.format = format;
        this.parseJavaPlugin();
    }

    /**
     * Parses the JavaPlugin to make sure it implements Api to get the running PluginDataManager
     */
    private void parseJavaPlugin() {

        if (this.plugin instanceof Api) {
            this.api = (Api) this.plugin;
            if (this.api.getPluginDataManager() == null) {

            }
            this.manager = this.api.getPluginDataManager();
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "JavaPlugin IS NOT EXTENDING 'Api' class!");
        }
    }

    @Override
    protected void onSave() {
        switch (format) {
            case JSON: {

                break;
            }

            case MYSQL:
            case MONGODB: {
                Bukkit.getLogger().log(Level.WARNING, "This saving method is not implemented yet!");
                break;
            }
        }
    }

    @Override
    protected void onReload() {
        switch (format) {
            case JSON: {

                break;
            }

            case MYSQL:
            case MONGODB: {
                break;
            }
        }
    }
}
