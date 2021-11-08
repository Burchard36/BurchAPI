package com.burchard36.json;

import com.burchard36.Api;
import com.burchard36.ApiLib;
import com.burchard36.json.errors.InvalidClassAdapterException;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import okio.BufferedSource;
import okio.Okio;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class PluginJsonWriter {

    private final JavaPlugin plugin;
    private final Moshi moshi;

    public PluginJsonWriter(final JavaPlugin plugin) {
        this.plugin = plugin;
        final Api apiLib = ApiLib.getLib(plugin);
        if (apiLib != null) {
            this.moshi = apiLib.getPluginDataManager().getMoshi();
        } else {
            this.moshi = null;
            Bukkit.getLogger().info("API :: ERROR! :: Error when grabbing moshi variables from Api implementing class," +
                    " as it does not implement Api!");
        }
    }

    public File createFile(final Config config) {
        final String configPath = config.configFilePath;
        final File file = new File(this.plugin.getDataFolder(), configPath + ".json");
        if (!file.exists()) {
            try {

                if (!file.getParentFile().exists()) if (file.getParentFile().mkdirs()) {
                    Bukkit.getLogger().info("API :: Successfully created directories");
                }

                if (file.createNewFile()) {
                    final JsonWriter writer = JsonWriter.of(Okio.buffer(Okio.sink(file)));
                    config.toJson(writer);
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new File(this.plugin.getDataFolder(), configPath + ".json");
    }

    public void writeDataToFile(final Config config) {
        File file = config.getFile();
        if (!file.exists()) {
            file = this.createFile(config);
        }

        try {
            final JsonWriter writer = JsonWriter.of(Okio.buffer(Okio.sink(file)));
            config.toJson(writer);
            writer.setIndent("    ");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public Config getDataFromFile(final Config config) {
        final String configPath = config.configFilePath;
        File file = new File(this.plugin.getDataFolder(), configPath + ".json");

        if (!file.exists()) {
            file = this.createFile(config);
        }

        try {
            final BufferedSource source = Okio.buffer(Okio.source(file));
            final JsonReader jsonReader = JsonReader.of(source);
            final JsonAdapter<? extends Config> classFileAdapter = moshi.adapter(config.getClass()).indent("    ");

            config.fromJson(jsonReader, classFileAdapter);
            jsonReader.close();
            return config;
        } catch (IOException | InvalidClassAdapterException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
