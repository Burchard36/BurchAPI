package com.burchard36.json;

import com.burchard36.Api;
import com.burchard36.ApiLib;
import com.burchard36.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class PluginJsonWriter {

    private final JavaPlugin plugin;
    private ObjectMapper mapper = null;

    public PluginJsonWriter(final JavaPlugin plugin) {
        this.plugin = plugin;
        final Api apiLib = ApiLib.getLib(plugin);
        if (apiLib != null) {
            this.mapper = apiLib.getPluginDataManager().getMapper();
        } else Logger.error("API :: ERROR! :: Error when grabbing moshi variables from Api implementing class," +
                    " as it does not implement Api!");
    }

    public File createFile(final JsonDataFile config) {
        final File file = config.getFile();
        if (!file.exists()) {
            try {

                if (!file.getParentFile().exists()) if (file.getParentFile().mkdirs()) {
                    Bukkit.getLogger().info("API :: Successfully created directories");
                }

                if (file.createNewFile()) {
                    this.mapper.writeValue(file, config);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return config.getFile();
    }

    public void writeDataToFile(final JsonDataFile config) {
        File file = config.getFile();
        if (!file.exists()) {
            file = this.createFile(config);
        }

        try {
            this.mapper.writeValue(file, config);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public JsonDataFile getDataFromFile(final JsonDataFile config) {
        File file = config.getFile();
        if (!file.exists()) file = this.createFile(config);

        try {
            return this.mapper.readValue(file, config.getClass());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
