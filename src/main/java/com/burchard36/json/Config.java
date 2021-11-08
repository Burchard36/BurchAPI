package com.burchard36.json;

import com.burchard36.Api;
import com.burchard36.ApiLib;
import com.burchard36.json.enums.FileFormat;
import com.burchard36.json.errors.InvalidClassAdapterException;
import com.burchard36.json.events.JsonLoadEvent;
import com.burchard36.json.events.JsonSaveEvent;
import com.squareup.moshi.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class Config {

    public transient String configFilePath;
    public transient JavaPlugin plugin;
    public transient Api api;
    final transient PluginJsonWriter jsonWriter;
    public transient FileFormat format;
    public transient PluginDataManager manager;

    public Config(final JavaPlugin plugin,
                  final String pathToFile,
                  final FileFormat format) {
        this.plugin = plugin;
        this.configFilePath = pathToFile;
        this.format = format;
        this.jsonWriter = new PluginJsonWriter(this.plugin);
        this.parseJavaPlugin();
    }

    /**
     * Parses the JavaPlugin to make sure it implements Api to get the running PluginDataManager
     */
    private void parseJavaPlugin() {
        this.api = ApiLib.getLib(this.plugin);
        if (this.api != null) {
            if (this.api.getPluginDataManager() == null) {
                Bukkit.getLogger().info("API :: ERROR :: PluginDataManager was null when parsing JavaPlugin");
                return;
            }
            this.manager = this.api.getPluginDataManager();
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "JavaPlugin IS NOT EXTENDING 'Api' class!");
        }
    }

    /***
     * Saves data to its own file
     */
    public void save() {
        this.jsonWriter.writeDataToFile(this);
    }

    public Config load() {
        return this.jsonWriter.getDataFromFile(this);
    }

    @FromJson
    public void fromJson(final JsonReader reader, JsonAdapter<? extends Config> classFileAdapter) throws IOException,
            InvalidClassAdapterException {}


    @ToJson
    public void toJson(JsonWriter writer) throws IOException {}

    public final File getFile() {
        return new File(this.plugin.getDataFolder(), this.configFilePath);
    }
}
