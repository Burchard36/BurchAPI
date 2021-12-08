package com.burchard36.json;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class JsonDataFile {

    public transient JavaPlugin plugin;
    public transient File file;

    public JsonDataFile(final JavaPlugin plugin,
                        String pathToFile) {
        this.plugin = plugin;

        if (!pathToFile.startsWith("/")) pathToFile = "/" + pathToFile;

        this.file = new File(this.plugin.getDataFolder(), pathToFile);
    }

    public final File getFile() {
        return this.file;
    }
}
