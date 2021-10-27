package com.burchard36.inventory;

import com.burchard36.json.Config;
import com.burchard36.json.enums.FileFormat;
import org.bukkit.plugin.java.JavaPlugin;

public class TestConfig extends Config {


    public TestConfig(JavaPlugin plugin, String pathToFile, FileFormat format) {
        super(plugin, pathToFile, format);

    }

    @Override
    public void onSave() {

    }

    @Override
    public void onReload() {

    }
}
