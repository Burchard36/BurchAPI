package com.burchard36.json;

import com.burchard36.Api;
import com.burchard36.ApiLib;
import com.burchard36.Logger;
import com.burchard36.json.enums.FileFormat;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class JsonDataFile {

    public transient JavaPlugin plugin;
    //public transient Api api;
    public transient FileFormat format;
    //public transient PluginDataManager manager;
    private final transient File file;

    public JsonDataFile(final JavaPlugin plugin,
                        final String pathToFile,
                        final FileFormat format) {
        this.plugin = plugin;
        this.format = format;
        this.file = new File(this.plugin.getDataFolder(), pathToFile);
        //this.parseJavaPlugin();
    }

    /**
     * Parses the JavaPlugin to make sure it implements Api to get the running PluginDataManager

    private void parseJavaPlugin() {
        this.api = ApiLib.getLib(this.plugin);
        if (this.api != null) {
            if (this.api.getPluginDataManager() == null) {
                Logger.error("API :: ERROR :: PluginDataManager was null when parsing JavaPlugin");
            } else this.manager = this.api.getPluginDataManager();
        } else Logger.error("JavaPlugin IS NOT EXTENDING 'Api' class!");
    }*/

    public final File getFile() {
        return this.file;
    }
}
