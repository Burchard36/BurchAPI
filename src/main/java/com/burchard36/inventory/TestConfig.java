package com.burchard36.inventory;

import com.burchard36.json.Config;
import com.burchard36.json.enums.FileFormat;
import com.burchard36.json.events.JsonSaveEvent;
import com.squareup.moshi.JsonWriter;
import org.bukkit.plugin.java.JavaPlugin;

public class TestConfig extends Config {


    public TestConfig(JavaPlugin plugin, String pathToFile, FileFormat format) {
        super(plugin, pathToFile, format);

    }

    @Override
    public void onSave(JsonSaveEvent saveEvent) {

    }

    @Override
    public void onWrite(JsonWriter writer) {

    }

    @Override
    public void onReload() {

    }
}
