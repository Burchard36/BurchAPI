package com.burchard36.json;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import okio.BufferedSource;
import okio.Okio;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PluginJsonWriter {

    private final JavaPlugin plugin;

    public PluginJsonWriter(final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void createFile(final Config config) {
        final String configPath = config.configFilePath;
        final File file = new File(this.plugin.getDataFolder(), configPath + ".json");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    final JsonWriter writer = JsonWriter.of(Okio.buffer(Okio.sink(file)));
                    config.write(writer);
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Config getDataFromFile(final Config config) {
        final String configPath = config.configFilePath;
        final File file = new File(this.plugin.getDataFolder(), configPath + ".json");

        try {
            final BufferedSource source = Okio.buffer(Okio.source(file));
            final JsonReader jsonReader = JsonReader.of(source);

            final Moshi moshi = new Moshi.Builder().build();
            final JsonAdapter<? extends Config> classFileAdapter = moshi.adapter(config.getClass());

            return classFileAdapter.fromJson(jsonReader);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
