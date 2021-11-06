package com.burchard36.json;

import com.burchard36.Api;
import com.burchard36.json.enums.FileFormat;
import com.burchard36.json.events.JsonLoadEvent;
import com.burchard36.json.events.JsonSaveEvent;
import com.squareup.moshi.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class Config extends ConfigFile {

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

        if (this.plugin instanceof Api) {
            this.api = (Api) this.plugin;
            if (this.api.getPluginDataManager() == null) {
                Bukkit.getLogger().info("API :: ERROR :: PluginDataManager was null when parsing JavaPlugin");
                return;
            }
            this.manager = this.api.getPluginDataManager();
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "JavaPlugin IS NOT EXTENDING 'Api' class!");
        }
    }

    @Override
    public void save() {
        this.jsonWriter.writeDataToFile(this);
    }

    @Override
    public Config load() {
        return this.jsonWriter.getDataFromFile(this);
    }

    @Override
    public void onLoad(JsonLoadEvent loadEvent) {

    }

    @Override
    protected void onSave(final JsonSaveEvent saveEvent) {

    }

    @Override
    protected void onReload() {

    }

    @Override
    protected void onWrite(JsonWriter writer) {

    }

    @Override
    protected void onRead(Config config) {

    }

    @Override
    File getFile() {
        return new File(this.plugin.getDataFolder(), this.configFilePath);
    }
}
