package com.burchard36.json;

import com.burchard36.json.enums.FileFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class JsonDataFile {

    @JsonIgnore
    public JavaPlugin plugin;
    @JsonIgnore
    public FileFormat format;
    @JsonIgnore
    private final File file;

    public JsonDataFile(final JavaPlugin plugin,
                        String pathToFile,
                        final FileFormat format) {
        this.plugin = plugin;
        this.format = format;

        if (!pathToFile.startsWith("/")) pathToFile = "/" + pathToFile;

        this.file = new File(this.plugin.getDataFolder(), pathToFile);
    }

    @JsonIgnore
    public final File getFile() {
        return this.file;
    }
}
