package com.burchard36.json;

import com.burchard36.json.enums.FileFormat;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class JsonDataFile {

    public transient JavaPlugin plugin;
    public transient FileFormat format;
    private final transient File file;

    public JsonDataFile(final JavaPlugin plugin,
                        String pathToFile,
                        final FileFormat format) {
        this.plugin = plugin;
        this.format = format;

        if (!pathToFile.startsWith("/")) pathToFile = "/" + pathToFile;

        this.file = new File(this.plugin.getDataFolder(), pathToFile);
    }

    public final File getFile() {
        return this.file;
    }
}
