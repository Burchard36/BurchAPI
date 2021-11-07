package com.burchard36.inventory;

import com.burchard36.json.Config;
import com.burchard36.json.enums.FileFormat;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class TestConfig extends Config {


    public TestConfig(JavaPlugin plugin, String pathToFile, FileFormat format) {
        super(plugin, pathToFile, format);

    }

    @Override
    public void toJson(JsonWriter writer) throws IOException {

    }

    @Override
    public void fromJson(JsonReader reader) throws IOException {
        super.fromJson(reader);
    }
}
