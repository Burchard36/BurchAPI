package com.burchard36.json;

import com.burchard36.json.events.JsonLoadEvent;
import com.burchard36.json.events.JsonSaveEvent;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import okio.BufferedSource;
import okio.Okio;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PluginJsonWriter {

    private final JavaPlugin plugin;

    public PluginJsonWriter(final JavaPlugin plugin) {
        this.plugin = plugin;
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
                    config.onWrite(writer);
                    writer.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return new File(this.plugin.getDataFolder(), configPath + ".json");
    }

    public Config writeDataToFile(final Config config) {
        final File file = config.getFile();
        if (!file.exists()) {
            this.createFile(config);
            return config;
        }

        try {
            final JsonWriter writer = JsonWriter.of(Okio.buffer(Okio.sink(file)));
            final JsonSaveEvent saveEvent = new JsonSaveEvent(file);
            config.onSave(saveEvent);
            if (saveEvent.isCancelled()) {
                writer.close();
                return config;
            }
            config.onWrite(writer);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return config;
    }

    public Config getDataFromFile(final Config config) {
        final String configPath = config.configFilePath;
        File file = new File(this.plugin.getDataFolder(), configPath + ".json");
        final JsonLoadEvent event = new JsonLoadEvent(file, null);

        if (!file.exists()) {
            file = this.createFile(config);
        }

        try {
            final BufferedSource source = Okio.buffer(Okio.source(file));
            final JsonReader jsonReader = JsonReader.of(source);

            final Moshi moshi = new Moshi.Builder().build();
            final JsonAdapter<? extends Config> classFileAdapter = moshi.adapter(config.getClass());

            event.config = classFileAdapter.fromJson(jsonReader);
            config.onLoad(event);
            if (event.isCancelled()) return config;
            return classFileAdapter.fromJson(jsonReader);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
